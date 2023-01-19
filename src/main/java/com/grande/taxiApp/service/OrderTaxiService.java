package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
import com.grande.taxiApp.repository.DriverRepository;
import com.grande.taxiApp.repository.OrderTaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderTaxiService {

    private final OrderTaxiRepository orderTaxiRepository;
    private final FuelPriceRepository fuelPriceRepository;
    private final CurrencyRepository currencyRepository;
    private final DriverRepository driverRepository;

    public OrderTaxi save(OrderTaxi orderTaxi){
        return orderTaxiRepository.save(orderTaxi);
    }
    public Optional<OrderTaxi> findById(Integer id){
        return orderTaxiRepository.findById(id);
    }
    public List<OrderTaxi> findAll(){
        return orderTaxiRepository.findAll();
    }
    public List<OrderTaxi> findByCustomerId(Integer id){
        return orderTaxiRepository.findByCustomerId(id);
    }
    public List<OrderTaxi> findByDriverId(Integer id){
        return orderTaxiRepository.findByDriverId(id);
    }
    public BigDecimal countTripPrice(Long distance){
        BigDecimal fuelPrice = fuelPriceRepository.findFuelPriceByCountry("Poland").get().getPrice();
        BigDecimal euroRate = currencyRepository.findByCurrency("euro").get().getPrice();
        BigDecimal priceOf1km = ((fuelPrice.multiply(euroRate))
                .multiply(BigDecimal.valueOf(9))).divide(BigDecimal.valueOf(100));
        BigDecimal cost =(priceOf1km.multiply(BigDecimal.valueOf(distance)))
                .multiply(BigDecimal.valueOf(2.15));

        if(LocalTime.now().isAfter(LocalTime.of(16,00)) &&
                LocalTime.now().isBefore(LocalTime.of(22,00))){
            return cost.multiply(BigDecimal.valueOf(1.30));
        }else if (LocalTime.now().isAfter(LocalTime.of(22,00)) &&
                LocalTime.now().isBefore(LocalTime.of(6,00))){
            return cost.multiply(BigDecimal.valueOf(1.35));
        }else if (LocalTime.now().isAfter(LocalTime.of(6,00)) &&
                LocalTime.now().isBefore(LocalTime.of(12,00))){
            return cost;
        }else{
            return cost.multiply(BigDecimal.valueOf(1.15));
        }
    }

    public void cancelOrderFouCustomersOnly(Integer id){
        Optional<OrderTaxi> byId = orderTaxiRepository.findById(id);
        Driver driver = new Driver(
                byId.get().getDriver().getId(),
                byId.get().getDriver().getName(),
                byId.get().getDriver().getSurname(),
                byId.get().getDriver().getPhoneNumber(),
                byId.get().getDriver().getEmail(),
                DriverStatus.ACTIVE,
                byId.get().getDriver().getCar());
        OrderTaxi cancelled = new OrderTaxi(
                byId.get().getId(),
                byId.get().getPickUpPlace(),
                byId.get().getDropPlace(),
                byId.get().getEstimatedCost(),
                byId.get().getEstimatedTime(),
                OrderTaxiStatus.CANCELLED,
                byId.get().getCustomer(),
                driver);
        driverRepository.save(driver);
        orderTaxiRepository.save(cancelled);
    }
    public void changeStatus(Integer id, String status){
        Optional<OrderTaxi> byId = orderTaxiRepository.findById(id);
        if (status.equalsIgnoreCase(OrderTaxiStatus.IN_PROGRESS.name())){
            orderTaxiRepository.save(changeStatus(byId.get(),OrderTaxiStatus.IN_PROGRESS));
        }if (status.equalsIgnoreCase(OrderTaxiStatus.FINISHED.name())){
            orderTaxiRepository.save(changeStatus(byId.get(),OrderTaxiStatus.FINISHED));
            driverRepository.save(changeDriverStatus(byId.get().getDriver(), DriverStatus.ACTIVE));
        }
    }
    private OrderTaxi changeStatus(OrderTaxi orderTaxi, OrderTaxiStatus status){
        return new OrderTaxi(orderTaxi.getId(), orderTaxi.getPickUpPlace(), orderTaxi.getDropPlace(),orderTaxi.getEstimatedCost(),orderTaxi.getEstimatedTime(),
                status,orderTaxi.getCustomer(),orderTaxi.getDriver());
    }
    private Driver changeDriverStatus(Driver driver,DriverStatus status){
        return new Driver(driver.getId(), driver.getName(), driver.getSurname(), driver.getPhoneNumber(), driver.getEmail(),status,driver.getCar() );
    }
    public List<OrderTaxi> getByStatus(OrderTaxiStatus status){
        return orderTaxiRepository.findByStatus(status);
    }
}

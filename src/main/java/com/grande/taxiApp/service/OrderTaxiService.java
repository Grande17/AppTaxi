package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.exceptions.OrderTaxiNotFoundException;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
import com.grande.taxiApp.mappers.OrderTaxiMapper;
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
    private final OrderTaxiMapper mapper;

    public OrderTaxi save(OrderTaxi orderTaxi){
        return orderTaxiRepository.save(orderTaxi);
    }
    public OrderTaxiFullDto findById(Integer id) throws OrderTaxiNotFoundException {
        Optional<OrderTaxi> byId = orderTaxiRepository.findById(id);
        if (byId.isEmpty()){
            throw new OrderTaxiNotFoundException();
        }else {
            return mapper.mapToOrderTaxiFullDto(byId.get());
        }
    }
    public List<OrderTaxiFullDto> findAll(){
        List<OrderTaxi> all = orderTaxiRepository.findAll();
        return mapper.mapToOrderTaxiFullDtoList(all);
    }
    public List<OrderTaxiFullDto> findByCustomerId(Integer id){
        List<OrderTaxi> byCustomerId = orderTaxiRepository.findByCustomerId(id);
        return mapper.mapToOrderTaxiFullDtoList(byCustomerId);
    }
    public List<OrderTaxiFullDto> findByDriverId(Integer id){
        List<OrderTaxi> byDriverId = orderTaxiRepository.findByDriverId(id);
        return mapper.mapToOrderTaxiFullDtoList(byDriverId);
    }
    public BigDecimal countTripPrice(Long distance) throws Exception {
        if (Optional.of(fuelPriceRepository.findFuelPriceByCountry("Poland").get().getPrice()).isEmpty()){
            throw new Exception("Fuel price is unknown");
        }else if (Optional.of(currencyRepository.findByCurrency("euro").get().getPrice()).isEmpty()){
            throw new Exception("Current currency rates are unkonown");
        }
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

    public void cancelOrderFouCustomersOnly(Integer id) throws OrderTaxiNotFoundException {
        Optional<OrderTaxi> byId = orderTaxiRepository.findById(id);
        if (byId.isEmpty()){
            throw new OrderTaxiNotFoundException();
        }
        byId.get().getDriver().setStatus(DriverStatus.ACTIVE);
        byId.get().setStatus(OrderTaxiStatus.CANCELLED);
        driverRepository.save(byId.get().getDriver());
        orderTaxiRepository.save(byId.get());

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
    public List<OrderTaxiFullDto> getByStatus(OrderTaxiStatus status){
        List<OrderTaxi> byStatus = orderTaxiRepository.findByStatus(status);
        return mapper.mapToOrderTaxiFullDtoList(byStatus);
    }
}

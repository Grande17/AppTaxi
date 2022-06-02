package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.enums.OrderTaxiStatus;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyRepository;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceRepository;
import com.grande.taxiapp.repository.DriverRepository;
import com.grande.taxiapp.repository.OrderTaxiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderTaxiService {

    private final OrderTaxiRepository orderTaxiRepository;
    private final FuelPriceRepository fuelPriceRepository;
    private final CurrencyRepository currencyRepository;
    private final DriverRepository driverRepository;

    public OrderTaxi save(OrderTaxi orderTaxi){
        return orderTaxiRepository.save(orderTaxi);
    }
    public void deleteById(Integer id){
        orderTaxiRepository.deleteById(id);
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

    public BigDecimal countTripPrice(Long distance){
        BigDecimal fuelPrice = fuelPriceRepository.findFuelPriceByCountry("Poland").get().getPrice();
        BigDecimal euroRate = currencyRepository.findByCurrency("euro").get().getValue();
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
}

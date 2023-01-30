package com.grande.taxiApp.domain.dto;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTaxiFullDto {

    private Integer id;
    private String pickUpPlace;
    private String dropPlace;
    private Customer customer;
    private BigDecimal estimatedCost;
    private LocalTime estimatedDuration;
    private OrderTaxiStatus status;
    private Driver driver;
}

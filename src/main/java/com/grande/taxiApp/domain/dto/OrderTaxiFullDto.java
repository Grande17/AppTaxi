package com.grande.taxiapp.domain.dto;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.enums.OrderTaxiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
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

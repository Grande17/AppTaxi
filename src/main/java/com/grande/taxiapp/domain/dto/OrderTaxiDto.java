package com.grande.taxiapp.domain.dto;

import com.grande.taxiapp.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTaxiDto {

    private Integer id;
    private String pickUpPlace;
    private String dropPlace;
    private Customer customer;
}

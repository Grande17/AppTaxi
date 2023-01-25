package com.grande.taxiApp.domain.dto;

import com.grande.taxiApp.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTaxiDto {

    private Integer id;
    private String pickUpPlace;
    private String dropPlace;
    private Customer customer;
}

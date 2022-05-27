package com.grande.taxiapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
     private Integer id;
     private String carBrand;
     private String model;
     private String bodyType;
     private String licensePlateNumber;


}

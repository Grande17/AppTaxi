package com.grande.taxiApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
     private Integer id;
     private String carBrand;
     private String model;
     private String bodyType;
     private String licensePlateNumber;


}

package com.grande.taxiApp.domain.dto;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private DriverStatus status;
    private Car car;
}

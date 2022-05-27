package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverMapper {

    public Driver mapToDriver(final DriverDto driverDto){
        return new Driver(
                driverDto.getId(),
                driverDto.getName(),
                driverDto.getSurname(),
                driverDto.getPhoneNumber(),
                driverDto.getEmail(),
                driverDto.getCar());
    }

    public DriverDto mapToDriverDto(final Driver driver){
        return new DriverDto(
                driver.getId(),
                driver.getName(),
                driver.getSurname(),
                driver.getPhoneNumber(),
                driver.getEmail(),
                driver.getCar());
    }

    public List<DriverDto> mapToListDto(final List<Driver> drivers){
        return drivers.stream()
                .map(this::mapToDriverDto)
                .collect(Collectors.toList());
    }
}

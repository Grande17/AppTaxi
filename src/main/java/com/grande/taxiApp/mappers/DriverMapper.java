package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverMapper {
    private final ModelMapper modelMapper;

    public Driver mapToDriver(DriverDto driverDto){
        return modelMapper.map(driverDto, Driver.class);
    }

    public DriverDto mapToDriverDto(Driver driver){
        return modelMapper.map(driver, DriverDto.class);
    }

    public List<DriverDto> mapToListDto(final List<Driver> drivers){
        return drivers.stream()
                .map(this::mapToDriverDto)
                .collect(Collectors.toList());
    }
}

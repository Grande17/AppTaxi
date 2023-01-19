package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.dto.CarDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMapper {

    public Car mapToCar(final CarDto carDto){
        return new Car(
                carDto.getId(),
                carDto.getCarBrand(),
                carDto.getModel(),
                carDto.getBodyType(),
                carDto.getLicensePlateNumber());
    }

    public CarDto mapToCarDto(final Car car){
        return new CarDto(
                car.getId(),
                car.getCarBrand(),
                car.getModel(),
                car.getBodyType(),
                car.getLicensePlateNumber());
    }
    public List<CarDto> mapToCarDtoList(final List<Car> cars){
        return cars.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }

}

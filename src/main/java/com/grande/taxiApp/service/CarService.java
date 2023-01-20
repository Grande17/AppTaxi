package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.exceptions.CarNotFoundException;
import com.grande.taxiApp.mappers.CarMapper;
import com.grande.taxiApp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {


    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarDto getCarById(Integer carId) throws CarNotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
        return carMapper.mapToCarDto(car);


    }

    public List<CarDto> findByPlatesContains(String platesNumber){
        List<Car> byLicensePlateNumberContains = carRepository.findByLicensePlateNumberContains(platesNumber);
        return carMapper.mapToCarDtoList(byLicensePlateNumberContains);
    }

    public void updateCar(CarDto carDto){
        carRepository.save(carMapper.mapToCar(carDto));
    }
    public List<CarDto> findAll(){
        List<Car> all = carRepository.findAll();
        return carMapper.mapToCarDtoList(all);
    }
}

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

    public Car getCarById(Integer carId) throws CarNotFoundException {
        return carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> findByPlatesContains(String platesNumber){
        return carRepository.findByLicensePlateNumberContains(platesNumber);
    }

    public void updateCar(CarDto carDto){
        carRepository.save(carMapper.mapToCar(carDto));
    }
    public List<Car> findAll(){
        return carRepository.findAll();
    }
}

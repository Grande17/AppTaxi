package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.exceptions.CarNotFoundException;
import com.grande.taxiapp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {


    @Autowired
    private CarRepository carRepository;

    public Car getCarById(Integer carId) throws CarNotFoundException {
        return carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
    }

    public List<Car> getCarByBrand(String brand){
        return carRepository.findByCarBrand(brand);
    }

    public Car saveCar(Car car){
        return carRepository.save(car);
    }
}

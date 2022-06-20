package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.dto.CarDto;
import com.grande.taxiapp.exceptions.CarNotFoundException;
import com.grande.taxiapp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiapp.mappers.CarMapper;
import com.grande.taxiapp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {


    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper carMapper;

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

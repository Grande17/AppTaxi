package com.grande.taxiapp.controllers;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.dto.CarDto;
import com.grande.taxiapp.exceptions.CarNotFoundException;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceClient;
import com.grande.taxiapp.mappers.CarMapper;
import com.grande.taxiapp.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;


    @PutMapping
    public ResponseEntity<Void> updateCar(@RequestBody CarDto carDto){
        carService.updateCar(carDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        carService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<CarDto>> getAll(){
        List<Car> all = carService.findAll();
        return ResponseEntity.ok(carMapper.mapToCarDtoList(all));
    }
    @GetMapping(value = "{platesNumber}")
    public ResponseEntity<CarDto> getCarByPlates(@PathVariable String platesNumber) throws CarNotFoundException {
        Car car = carService.findByPlates(platesNumber);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<CarDto> findCarById(@PathVariable Integer id) throws CarNotFoundException {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }





}

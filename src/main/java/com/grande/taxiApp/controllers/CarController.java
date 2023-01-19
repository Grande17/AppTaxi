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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;


    @PutMapping
    public ResponseEntity<Void> updateCar(@RequestBody @Valid CarDto carDto){
        carService.updateCar(carDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<CarDto>> getAll(){
        List<Car> all = carService.findAll();
        return ResponseEntity.ok(carMapper.mapToCarDtoList(all));
    }
    @GetMapping(value = "/plates/{platesNumber}")
    public ResponseEntity<List<CarDto>> getCarByPlates(@PathVariable String platesNumber){
        List<Car> car = carService.findByPlatesContains(platesNumber);
        return ResponseEntity.ok(carMapper.mapToCarDtoList(car));
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<CarDto> findCarById(@PathVariable Integer id) throws CarNotFoundException {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }





}

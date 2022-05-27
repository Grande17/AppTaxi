package com.grande.taxiapp.controllers;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.dto.CarDto;
import com.grande.taxiapp.domain.dto.FuelPriceListDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceClient;
import com.grande.taxiapp.mappers.CarMapper;
import com.grande.taxiapp.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    private final FuelPriceClient fuelPriceClient;

    @PutMapping
    public ResponseEntity<Void> updateCar(@RequestBody CarDto carDto){
        Car car = carMapper.mapToCar(carDto);
        carService.saveCar(car);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public FuelPriceListDto jsjsj(){
        return fuelPriceClient.getFuelPrice();
    }



}

package com.grande.taxiApp.controllers;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.exceptions.CarNotFoundException;
import com.grande.taxiApp.mappers.CarMapper;
import com.grande.taxiApp.service.CarService;
import lombok.RequiredArgsConstructor;
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

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



    @PutMapping
    public ResponseEntity<Void> updateCar(@RequestBody @Valid CarDto carDto){
        carService.updateCar(carDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<CarDto>> getAll(){
        return ResponseEntity.ok(carService.findAll());
    }
    @GetMapping(value = "/plates/{platesNumber}")
    public ResponseEntity<List<CarDto>> getCarByPlates(@PathVariable String platesNumber){
        return ResponseEntity.ok(carService.findByPlatesContains(platesNumber));
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<CarDto> findCarById(@PathVariable Integer id) throws CarNotFoundException {
        return ResponseEntity.ok(carService.getCarById(id));
    }





}

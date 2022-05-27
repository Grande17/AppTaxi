package com.grande.taxiapp.controllers;


import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import com.grande.taxiapp.exceptions.DriverNotFoundException;
import com.grande.taxiapp.mappers.DriverMapper;
import com.grande.taxiapp.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverMapper driverMapper;
    private final DriverService driverService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewDriverAcc(@RequestBody DriverDto driverDto){
        Driver driver = driverMapper.mapToDriver(driverDto);
        driverService.saveDriver(driver);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<DriverDto>> getAll(){
        List<Driver> all = driverService.getAll();
        return ResponseEntity.ok(driverMapper.mapToListDto(all));
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Integer id) throws DriverNotFoundException{
        return ResponseEntity.ok(driverMapper.mapToDriverDto(driverService.findById(id).orElseThrow(DriverNotFoundException::new)));
    }
    @PutMapping
    public ResponseEntity<Void> updateDriver(@RequestBody DriverDto driverDto){
        Driver driver = driverMapper.mapToDriver(driverDto);
        driverService.saveDriver(driver);
        return ResponseEntity.ok().build();
    }
}

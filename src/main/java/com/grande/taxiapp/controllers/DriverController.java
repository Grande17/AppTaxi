package com.grande.taxiapp.controllers;


import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import com.grande.taxiapp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiapp.exceptions.DriverNotFoundException;
import com.grande.taxiapp.exceptions.EmailException;
import com.grande.taxiapp.mappers.DriverMapper;
import com.grande.taxiapp.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/drivers")
@RequiredArgsConstructor
@Valid
public class DriverController {

    private final DriverMapper driverMapper;
    private final DriverService driverService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewDriverAcc(@RequestBody @Valid DriverDto driverDto) throws EmailException, CarWithGivenPlatesException {
        driverService.saveDriver(driverDto);
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
    public ResponseEntity<Void> updateDriver(@RequestBody @Valid DriverDto driverDto){
        driverService.updateDriver(driverDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteDriverAcc(@PathVariable Integer id){
        driverService.deleteDriverById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/name/{contains}")
    public ResponseEntity<List<DriverDto>> getByNameAndSurnameContains(@PathVariable String contains){
        return ResponseEntity.ok(driverMapper.mapToListDto(driverService.findBySurname(contains)));
    }
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<DriverDto>> findByEmailContains(@PathVariable String email){
        return ResponseEntity.ok(driverMapper.mapToListDto(driverService.findByEmailContains(email)));
    }
    @PutMapping(value = "/status/{id}/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @PathVariable String status) throws DriverNotFoundException {
        driverService.updateStatus(id,status);
        return ResponseEntity.ok().build();
    }
}

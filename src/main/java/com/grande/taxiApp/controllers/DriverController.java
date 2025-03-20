package com.grande.taxiApp.controllers;


import com.grande.taxiApp.domain.dto.DriverDto;
import com.grande.taxiApp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiApp.exceptions.DriverNotFoundException;
import com.grande.taxiApp.exceptions.EmailException;
import com.grande.taxiApp.service.DriverService;
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


    private final DriverService driverService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewDriverAcc(@RequestBody @Valid DriverDto driverDto) throws EmailException, CarWithGivenPlatesException {
        driverService.saveDriver(driverDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<DriverDto>> getAll(){
        return ResponseEntity.ok(driverService.getAll());
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Integer id) throws DriverNotFoundException {
        return ResponseEntity.ok(driverService.findById(id));
    }
    @PutMapping
    public ResponseEntity<Void> updateDriver(@RequestBody @Valid DriverDto driverDto) throws DriverNotFoundException {
        driverService.updateDriver(driverDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteDriverAcc(@PathVariable Integer id) throws DriverNotFoundException {
        driverService.deleteDriverById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/name/{contains}")
    public ResponseEntity<List<DriverDto>> getByNameAndSurnameContains(@PathVariable String contains){
        return ResponseEntity.ok(driverService.findBySurname(contains));
    }
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<DriverDto>> findByEmailContains(@PathVariable String email){
        return ResponseEntity.ok(driverService.findByEmailContains(email));
    }
    @PutMapping(value = "/status/{id}/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @PathVariable String status) throws Exception {
        driverService.updateStatus(id,status);
        return ResponseEntity.ok().build();
    }
}

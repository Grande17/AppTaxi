package com.grande.taxiApp.controllers;


import com.grande.taxiApp.domain.dto.CustomerDto;
import com.grande.taxiApp.exceptions.CustomerNotFoundException;
import com.grande.taxiApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewCustomerAcc(@RequestBody @Valid CustomerDto customerDto){
        customerService.saveCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerDto customerDto){
        customerService.saveCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer id)throws CustomerNotFoundException{
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        return ResponseEntity.ok(customerService.findAll());
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/username/{username}")
    public ResponseEntity<List<CustomerDto>> getByContains(@PathVariable String username) {
        return ResponseEntity.ok(customerService.findByUsername(username));
    }
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<CustomerDto>> getByName(@PathVariable String name){
        return ResponseEntity.ok(customerService.findByName(name));
    }
    @PutMapping(value = "/email/{id}/{email}")
    public ResponseEntity<Void> updateEmail(@PathVariable Integer id, @PathVariable String email) throws CustomerNotFoundException {
        customerService.updateEmail(id,email);
        return ResponseEntity.ok().build();
    }
}

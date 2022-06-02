package com.grande.taxiapp.controllers;


import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.dto.CustomerDto;
import com.grande.taxiapp.exceptions.CustomerNotFoundException;
import com.grande.taxiapp.mappers.CustomerMapper;
import com.grande.taxiapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewCustomerAcc(@RequestBody CustomerDto customerDto){
        Customer customer = customerMapper.mapToCustomer(customerDto);
        customerService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerMapper.mapToCustomer(customerDto);
        customerService.saveCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer id)throws CustomerNotFoundException{
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customerService.findCustomerById(id)));
    }
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        List<Customer> findAll = customerService.findAll();
        return ResponseEntity.ok(customerMapper.mapToCustomerDtoList(findAll));
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}

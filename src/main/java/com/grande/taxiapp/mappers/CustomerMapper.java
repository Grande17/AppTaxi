package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    public Customer mapToCustomer(final CustomerDto customerDto){
        return new Customer(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getSurname(),
                customerDto.getUsername(),
                customerDto.getPhoneNumber(),
                customerDto.getEmail());
    }
    public CustomerDto mapToCustomerDto(final Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getUsername(),
                customer.getPhoneNumber(),
                customer.getEmail());
    }
    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customers){
        return customers.stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }
}

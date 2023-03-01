package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public Customer mapToCustomer(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);

    }
    public CustomerDto mapToCustomerDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customers){
        return customers.stream()
                .map(this::mapToCustomerDto)
                .toList();
    }
}

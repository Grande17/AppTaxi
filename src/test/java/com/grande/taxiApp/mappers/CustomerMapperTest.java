package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerMapperTest {

    @Autowired
    private CustomerMapper mapper;

    @Test
    void mapToCustomer() {
        //Given
        CustomerDto customerDto = new CustomerDto(1,"name","surname","username","000999888","email");
        //When
        Customer customer = mapper.mapToCustomer(customerDto);
        //Then
        assertEquals(customerDto.getId(),customer.getId());
        assertEquals(customerDto.getName(),customer.getName());
        assertEquals(customerDto.getSurname(),customer.getSurname());
        assertEquals(customerDto.getUsername(),customerDto.getUsername());
        assertEquals(customerDto.getPhoneNumber(),customerDto.getPhoneNumber());
        assertEquals(customerDto.getEmail(),customerDto.getEmail());
    }

    @Test
    void mapToCustomerDto() {
        //Given
        Customer customer = new Customer.Builder()
                .name("Test")
                .surname("Test")
                .username("Test")
                .phoneNumber("000999888")
                .email("test@email.com").build();
        //When
        CustomerDto customerDto = mapper.mapToCustomerDto(customer);
        //Then
        assertEquals(customer.getId(),customerDto.getId());
        assertEquals(customer.getName(),customerDto.getName());
        assertEquals(customer.getSurname(),customerDto.getSurname());
        assertEquals(customer.getUsername(),customerDto.getUsername());
        assertEquals(customer.getPhoneNumber(),customerDto.getPhoneNumber());
        assertEquals(customer.getEmail(),customerDto.getEmail());
    }

    @Test
    void mapToCustomerDtoList() {
        //Given
        Customer customer1 = new Customer();
        customer1.setName("Test");
        customer1.setSurname("Test");
        customer1.setUsername("Test");
        customer1.setPhoneNumber("000999888");
        customer1.setEmail("test@email.com");
        List<Customer> customerList = Arrays.asList(customer1, new Customer("name","surname","username","000999888","email"));
        //When
        List<CustomerDto>dtos = mapper.mapToCustomerDtoList(customerList);
        //Then
        assertEquals(customerList.size(),dtos.size());
    }
}
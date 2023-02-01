package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.grande.taxiApp.ResourceFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerMapperTest {

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CustomerMapper mapper;

    @Test
    void mapToCustomer() {
        //Given
        when(modelMapper.map(customerDto,Customer.class)).thenReturn(customer);
        //When
        Customer customer = mapper.mapToCustomer(customerDto);
        //Then
        assertEquals(customerDto.getName(),customer.getName());
        assertEquals(customerDto.getSurname(),customer.getSurname());
        assertEquals(customerDto.getUsername(),customerDto.getUsername());
        assertEquals(customerDto.getPhoneNumber(),customerDto.getPhoneNumber());
        assertEquals(customerDto.getEmail(),customerDto.getEmail());
    }

    @Test
    void mapToCustomerDto() {
        //Given
        when(modelMapper.map(customer, CustomerDto.class)).thenReturn(customerDto);
        //When
        CustomerDto customerDto = mapper.mapToCustomerDto(customer);
        //Then
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
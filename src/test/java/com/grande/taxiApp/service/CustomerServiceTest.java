package com.grande.taxiApp.service;

import com.grande.taxiApp.ResourceFactory;
import com.grande.taxiApp.domain.dto.CustomerDto;
import com.grande.taxiApp.exceptions.CustomerNotFoundException;
import com.grande.taxiApp.mappers.CustomerMapper;
import com.grande.taxiApp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {



    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private CustomerService customerService = mock(CustomerService.class);
    private CustomerMapper mapper = mock(CustomerMapper.class);

    @Test
    void saveCustomerTest(){
        when(customerService.saveCustomer(any())).thenReturn(null);
        when(mapper.mapToCustomerDto(any())).thenReturn(ResourceFactory.customerDto);
        when(customerRepository.save(any())).thenReturn(null);
        //when
        customerService.saveCustomer(ResourceFactory.customerDto);
        //then
        verify(customerService,times(1)).saveCustomer(any(CustomerDto.class));
    }


    @Test
    void deleteCustomerById() {
        //when
        customerService.deleteCustomerById(10);
        //then
        Mockito.verify(customerService,Mockito.times(1)).deleteCustomerById(10);
    }

    @Test
    void findCustomerById() throws CustomerNotFoundException {
        //given
        when(customerService.findCustomerById(anyInt())).thenReturn(ResourceFactory.customerDto);
        when(mapper.mapToCustomerDto(any())).thenReturn(ResourceFactory.customerDto);
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(ResourceFactory.customer));
        //when
        CustomerDto result = customerService.findCustomerById(77);
        //then
        assertEquals(ResourceFactory.customerDto.getEmail(),result.getEmail());
    }

    @Test
    void findAll() {
        //given
        when(customerService.findAll()).thenReturn(ResourceFactory.customerDtoList);
        when(mapper.mapToCustomerDtoList(any())).thenReturn(ResourceFactory.customerDtoList);
        when(customerRepository.findAll()).thenReturn(ResourceFactory.customerList);
        //when
        List<CustomerDto> result = customerService.findAll();
        //then
        assertEquals(ResourceFactory.customerDtoList.size(),result.size());
    }

    @Test
    void findByUsername() {
        //given
        when(customerService.findByUsername(anyString())).thenReturn(ResourceFactory.customerDtoList);
        when(mapper.mapToCustomerDtoList(any())).thenReturn(ResourceFactory.customerDtoList);
        when(customerRepository.findByUsernameContains(anyString())).thenReturn(ResourceFactory.customerList);
        //when
        List<CustomerDto> result = customerService.findByUsername("te");
        //then
        assertEquals(ResourceFactory.customerDtoList.size(),result.size());
    }

    @Test
    void findByName() {
        when(customerService.findByName(anyString())).thenReturn(ResourceFactory.customerDtoList);
        when(mapper.mapToCustomerDtoList(anyList())).thenReturn(ResourceFactory.customerDtoList);
        when(customerRepository.findByNameContains(anyString())).thenReturn(ResourceFactory.customerList);
        //when
        List<CustomerDto> result = customerService.findByName("te");
        //then
        assertEquals(ResourceFactory.customerDtoList.size(),result.size());
    }

    @Test
    void updateEmail() throws CustomerNotFoundException {

        customerService.updateEmail(1,"newemail");
        //then
        verify(customerService,timeout(1)).updateEmail(anyInt(),anyString());

    }


}
package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.exceptions.CustomerNotFoundException;
import com.grande.taxiapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void saveCustomerTest(){
        //given
        Customer customer = new Customer(1,"test","test","test","test","test");
        //when
        customerService.saveCustomer(customer);
        //then
        verify(customerRepository,times(1)).save(any(Customer.class));
    }


    @Test
    void deleteCustomerById() {
        //when
        customerService.deleteCustomerById(10);
        //then
        Mockito.verify(customerRepository,Mockito.times(1)).deleteById(10);
    }

    @Test
    void findCustomerById() throws CustomerNotFoundException {
        //given
        Customer customer = new Customer(1,"test","test","test","test","test");
        doReturn(Optional.of(customer)).when(customerRepository).findById(Mockito.any());
        //when
        Customer result = customerService.findCustomerById(77);
        //then
        assertEquals(customer.getEmail(),result.getEmail());
    }

    @Test
    void findAll() {
        //given
        List<Customer> customerList = Arrays.asList(new Customer(1,"test","test","test","test","test"),
                new Customer(2,"test","test","test","test","test"));
        when(customerRepository.findAll()).thenReturn(customerList);
        //when
        List<Customer> result = customerService.findAll();
        //then
        assertEquals(customerList.size(),result.size());
    }

    @Test
    void findByUsername() {
        //given
        List<Customer> customerList = Arrays.asList(new Customer(1,"test","test","test","test","test"),
                new Customer(2,"test","test","test","test","test"));
        when(customerRepository.findByUsernameContains(anyString())).thenReturn(customerList);
        //when
        List<Customer> result = customerService.findByUsername("te");
        //then
        assertEquals(customerList.size(),result.size());
    }

    @Test
    void findByName() {
        List<Customer> customerList = Arrays.asList(new Customer(1,"test","test","test","test","test"),
                new Customer(2,"test","test","test","test","test"));
        when(customerRepository.findByNameContains(anyString())).thenReturn(customerList);
        //when
        List<Customer> result = customerService.findByName("te");
        //then
        assertEquals(customerList.size(),result.size());
    }

    @Test
    void updateEmail() throws CustomerNotFoundException {
        Customer customer = new Customer(1,"test","test","test","test","test");
        when(customerRepository.save(any(Customer.class))).thenReturn(null);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        customerService.updateEmail(1,"newemail");
        //then
        verify(customerRepository,times(1)).save(any(Customer.class));
    }
}
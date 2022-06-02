package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.exceptions.CustomerNotFoundException;
import com.grande.taxiapp.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public void deleteCustomerById(Integer id){
        customerRepository.deleteById(id);
    }

    public Customer findCustomerById(Integer id) throws CustomerNotFoundException {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

}

package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.exceptions.CustomerNotFoundException;
import com.grande.taxiApp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
    public List<Customer> findByUsername(String username){
        return customerRepository.findByUsernameContains(username);
    }
    public List<Customer> findByName(String name){
        return customerRepository.findByNameContains(name);
    }
    public void updateEmail(Integer id, String email) throws CustomerNotFoundException {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()){
            byId.get().setEmail(email);
        }else{
            throw new CustomerNotFoundException();
        }
    }



}

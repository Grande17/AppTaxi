package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.exceptions.CustomerNotFoundException;
import com.grande.taxiapp.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

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
    public List<Customer> findByUsername(String username){
        return customerRepository.findByUsernameContains(username);
    }
    public List<Customer> findByName(String name){
        return customerRepository.findByNameContains(name);
    }
    public void updateEmail(Integer id, String email) throws CustomerNotFoundException {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()){
            Customer updated = new Customer.Builder()
                    .id(byId.get().getId())
                    .name(byId.get().getName())
                    .surname(byId.get().getSurname())
                    .username(byId.get().getUsername())
                    .phoneNumber(byId.get().getPhoneNumber())
                    .email(email)
                    .build();
            customerRepository.save(updated);
        }else{
            throw new CustomerNotFoundException();
        }
    }



}

package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.dto.CustomerDto;
import com.grande.taxiApp.exceptions.CustomerNotFoundException;
import com.grande.taxiApp.mappers.CustomerMapper;
import com.grande.taxiApp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;


    public Customer saveCustomer(CustomerDto customer){
        Customer customer1 = mapper.mapToCustomer(customer);
        return customerRepository.save(customer1);
    }
    public void deleteCustomerById(Integer id){
        customerRepository.deleteById(id);
    }

    public CustomerDto findCustomerById(Integer id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        return mapper.mapToCustomerDto(customer);
    }
    public List<CustomerDto> findAll(){
        List<Customer> all = customerRepository.findAll();
        return mapper.mapToCustomerDtoList(all);
    }
    public List<CustomerDto> findByUsername(String username){
        List<Customer> byUsernameContains = customerRepository.findByUsernameContains(username);
        return mapper.mapToCustomerDtoList(byUsernameContains);
    }
    public List<CustomerDto> findByName(String name){
        List<Customer> byNameContains = customerRepository.findByNameContains(name);
        return mapper.mapToCustomerDtoList(byNameContains);
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

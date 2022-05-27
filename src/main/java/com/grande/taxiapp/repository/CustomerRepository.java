package com.grande.taxiapp.repository;

import com.grande.taxiapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAll();
    Optional<Customer> findById(Integer id);
    List<Customer> findByName(String name);
    List<Customer> findBySurname(String surname);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer customer);
    void deleteById(Integer id);
}

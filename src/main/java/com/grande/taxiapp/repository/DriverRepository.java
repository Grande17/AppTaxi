package com.grande.taxiapp.repository;

import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    List<Driver> findAll();
    Optional<Driver> findById(Integer id);
    List<Driver> findByName(String name);
    List<Driver> findBySurname(String surname);
    Optional<Driver> findByPhoneNumber(String phoneNumber);
    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByCarId(String carId);
    List<Driver> findByStatus(DriverStatus status);
    Driver save(Driver driver);
    void deleteById(Integer id);
}

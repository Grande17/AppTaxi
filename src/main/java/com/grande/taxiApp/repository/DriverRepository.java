package com.grande.taxiApp.repository;

import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.enums.DriverStatus;
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
    List<Driver> findBySurnameContains(String surname);
    Optional<Driver> findByEmail(String email);
    List<Driver> findByEmailContains(String email);
    List<Driver> findByStatus(DriverStatus status);
    Driver save(Driver driver);
    void deleteById(Integer id);
}

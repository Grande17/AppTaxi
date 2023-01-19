package com.grande.taxiApp.repository;

import com.grande.taxiApp.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findAll();
    Optional<Car> findById(Integer id);

    Optional<Car> findByLicensePlateNumber(String licensePlateNumber);
    List<Car> findByLicensePlateNumberContains(String contains);
    Car save(Car car);

}

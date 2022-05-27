package com.grande.taxiapp.repository;

import com.grande.taxiapp.domain.Car;
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
    List<Car> findByCarBrand(String carBrand);
    List<Car> findByModel(String model);
    List<Car> findByLicensePlateNumber(String licensePlateNumber);
    Car save(Car car);
    void deleteById(Integer id);
}

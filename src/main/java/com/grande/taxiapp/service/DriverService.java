package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver saveDriver(final Driver driver){
        return driverRepository.save(driver);
    }
    public List<Driver> getAll(){
        return driverRepository.findAll();
    }
    public Optional<Driver> findById(Integer id){
        return driverRepository.findById(id);
    }

    public void deleteDriverById(Integer id){
        driverRepository.deleteById(id);
    }
}

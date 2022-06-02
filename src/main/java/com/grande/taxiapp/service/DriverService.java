package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiapp.exceptions.EmailException;
import com.grande.taxiapp.mappers.DriverMapper;
import com.grande.taxiapp.repository.CarRepository;
import com.grande.taxiapp.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final DriverMapper mapper;

    public Driver saveDriver(final DriverDto driverDto) throws CarWithGivenPlatesException, EmailException {
        if (verifyDriver(driverDto)){
            if (verifyCar(driverDto)){
                return driverRepository.save(mapper.mapToDriver(driverDto));
            }else{
                throw new CarWithGivenPlatesException();
            }
        }else{
            throw new EmailException();
        }
    }
    public Driver updateDriver(DriverDto driverDto){
        Driver driver = mapper.mapToDriver(driverDto);
        return driverRepository.save(driver);
    }
    public List<Driver> getAll(){
        return driverRepository.findAll();
    }
    public Optional<Driver> findById(Integer id){
        return driverRepository.findById(id);
    }

    public void deleteDriverById(Integer id){
        Optional<Driver> byId = driverRepository.findById(id);
        Driver driver = new Driver(
                byId.get().getId(),
                byId.get().getName(),
                byId.get().getSurname(),
                byId.get().getPhoneNumber(),
                byId.get().getEmail(),
                DriverStatus.ACCOUNT_DELETED,
                byId.get().getCar());
        driverRepository.save(driver);

    }
    private boolean verifyDriver(DriverDto driverDto){
        return driverRepository.findByEmail(driverDto.getEmail()).isEmpty();
    }
    private boolean verifyCar(DriverDto driverDto){
        return carRepository.findByLicensePlateNumber(driverDto.getCar().getLicensePlateNumber()).isEmpty();
    }
}

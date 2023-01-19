package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiapp.exceptions.DriverNotFoundException;
import com.grande.taxiapp.exceptions.EmailException;
import com.grande.taxiapp.mappers.DriverMapper;
import com.grande.taxiapp.repository.CarRepository;
import com.grande.taxiapp.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final DriverMapper mapper;

    public Driver saveDriver(final DriverDto driverDto) throws CarWithGivenPlatesException, EmailException {
        if (verifyDriver(driverDto)){
            if (verifyCar(driverDto)){
                Driver driver = new Driver(
                        driverDto.getId(),
                        driverDto.getName(), driverDto.getSurname(),
                        driverDto.getPhoneNumber(),
                        driverDto.getEmail(),
                        new Car(driverDto.getCar().getId(),driverDto.getCar().getCarBrand(),driverDto.getCar().getModel(),
                                driverDto.getCar().getBodyType(),driverDto.getCar().getLicensePlateNumber())
                );
                return driverRepository.save(driver);
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
         List<Driver> drivers = driverRepository.findAll().stream()
                 .filter(x->!x.getStatus().equals(DriverStatus.ACCOUNT_DELETED))
                 .collect(Collectors.toList());
         return drivers;

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
    public List<Driver> findBySurname(String contains){
        return driverRepository.findBySurnameContains(contains);
    }
    public List<Driver> findByEmailContains(String email){
        return driverRepository.findByEmailContains(email);
    }
    public Driver updateStatus(Integer id, String status) throws DriverNotFoundException {
        Optional<Driver> byId = driverRepository.findById(id);
        if (byId.isPresent()){
            Driver driver = new Driver();
            driver.setId(byId.get().getId());
            driver.setName(byId.get().getName());
            driver.setSurname(byId.get().getSurname());
            driver.setEmail(byId.get().getEmail());
            driver.setPhoneNumber(byId.get().getPhoneNumber());
            driver.setCar(byId.get().getCar());
            switch (status){
                case "ACTIVE":
                    driver.setStatus(DriverStatus.ACTIVE);
                    break;
                case "BUSY":
                    driver.setStatus(DriverStatus.BUSY);
                    break;
                case "BREAK":
                    driver.setStatus(DriverStatus.BREAK);
                    break;
                case "INACTIVE":
                    driver.setStatus(DriverStatus.INACTIVE);
                    break;
            }
            return driverRepository.save(driver);
        }else{
            throw new DriverNotFoundException();
        }

    }
}

package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.dto.DriverDto;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiApp.exceptions.DriverNotFoundException;
import com.grande.taxiApp.exceptions.EmailException;
import com.grande.taxiApp.mappers.DriverMapper;
import com.grande.taxiApp.repository.CarRepository;
import com.grande.taxiApp.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        return driverRepository.findAll().stream()
                 .filter(x->!x.getStatus().equals(DriverStatus.ACCOUNT_DELETED))
                 .collect(Collectors.toList());

    }
    public Optional<Driver> findById(Integer id){
        return driverRepository.findById(id);
    }

    public void deleteDriverById(Integer id){
        Optional<Driver> byId = driverRepository.findById(id);
        byId.get().setStatus(DriverStatus.ACCOUNT_DELETED);

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

            switch (status){
                case "ACTIVE":
                    byId.get().setStatus(DriverStatus.ACTIVE);
                    break;
                case "BUSY":
                    byId.get().setStatus(DriverStatus.BUSY);
                    break;
                case "BREAK":
                    byId.get().setStatus(DriverStatus.BREAK);
                    break;
                case "INACTIVE":
                    byId.get().setStatus(DriverStatus.INACTIVE);
                    break;
            }
            return driverRepository.save(byId.get());
        }else{
            throw new DriverNotFoundException();
        }

    }
}

package com.grande.taxiApp.service;

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

    public Driver saveDriver(DriverDto driverDto) throws CarWithGivenPlatesException, EmailException {
        if (verifyDriver(driverDto)){
            if (verifyCar(driverDto)){
                Driver driver = mapper.mapToDriver(driverDto);
                return driverRepository.save(driver);
            }else{
                throw new CarWithGivenPlatesException();
            }
        }else{
            throw new EmailException();
        }
    }
    public void updateDriver(DriverDto driverDto) throws DriverNotFoundException {
        Optional<Driver> driver = driverRepository.findById(driverDto.getId());
        if (driver.isPresent()) {
            Driver driver1 = mapper.mapToDriver(driverDto);
            driverRepository.save(driver1);
        }else{
            throw new DriverNotFoundException();
        }
    }
    public List<DriverDto> getAll(){
        List<Driver> collect = driverRepository.findAll().stream()
                .filter(x -> !x.getStatus().equals(DriverStatus.ACCOUNT_DELETED))
                .toList();
        List<DriverDto> driverDtos = mapper.mapToListDto(collect);
        return driverDtos;

    }
    public DriverDto findById(Integer id) throws DriverNotFoundException {
        Optional<Driver> byId = driverRepository.findById(id);
        if (byId.isEmpty()){
            throw new DriverNotFoundException();
        }else {
            return mapper.mapToDriverDto(byId.get());
        }
    }

    public void deleteDriverById(Integer id) throws DriverNotFoundException {
        Optional<Driver> byId = driverRepository.findById(id);
        if (byId.isEmpty()){
            throw new DriverNotFoundException();
        }
        byId.get().setStatus(DriverStatus.ACCOUNT_DELETED);
        driverRepository.save(byId.get());


    }
    protected boolean verifyDriver(DriverDto driverDto){
        return driverRepository.findByEmail(driverDto.getEmail()).isEmpty();
    }
    protected boolean verifyCar(DriverDto driverDto){
        return carRepository.findByLicensePlateNumber(driverDto.getCar().getLicensePlateNumber()).isEmpty();
    }
    public List<DriverDto> findBySurname(String contains){
        List<Driver> bySurnameContains = driverRepository.findBySurnameContains(contains);
        return mapper.mapToListDto(bySurnameContains);
    }
    public List<DriverDto> findByEmailContains(String email){
        List<Driver> byEmailContains = driverRepository.findByEmailContains(email);
        return mapper.mapToListDto(byEmailContains);
    }
    public Driver updateStatus(Integer id, String status) throws Exception {
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
                default: throw new Exception("Invalid status");
            }
            return driverRepository.save(byId.get());
        }else{
            throw new DriverNotFoundException();
        }

    }
}

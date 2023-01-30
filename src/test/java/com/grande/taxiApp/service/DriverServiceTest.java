package com.grande.taxiApp.service;

import com.grande.taxiApp.Exes;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository repository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private DriverMapper mapper;
    @InjectMocks
    private DriverService service;

    @Test
    void saveDriver() throws EmailException, CarWithGivenPlatesException {
        //given
       when(service.saveDriver(Exes.driverDto)).thenReturn(Exes.driver);
        when(repository.save(any())).thenReturn(null);
       when(mapper.mapToDriver(Exes.driverDto)).thenReturn(Exes.driver);
       when(carRepository.save(Exes.driver.getCar())).thenReturn(Exes.driver.getCar());
       when(service.verifyCar(any(DriverDto.class))).thenReturn(true);
       when(any(DriverDto.class).getCar()).thenReturn(Exes.car);
       when(service.verifyDriver(any())).thenReturn(true);
        //when
        service.saveDriver(Exes.driverDto);
        //then
        verify(repository,times(1)).save(any(Driver.class));

    }

    @Test
    void updateDriver() throws DriverNotFoundException {
        //given
        when(service.findById(any())).thenReturn(Exes.driverDto);
        when(mapper.mapToDriver(any(DriverDto.class))).thenReturn(new Driver());
        //when
        service.updateDriver(Exes.driverDto);
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }

    @Test
    void getAll() {
        //given
        when(repository.findAll()).thenReturn(Exes.driversList);
        //when
        List<DriverDto> result = service.getAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(Exes.driversDtoList.size(),result.size());

    }

    @Test
    void findById() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(Exes.driver));
        //when
        Optional<DriverDto> result = Optional.ofNullable(service.findById(1));
        //then
        assertEquals(result.get().getId(),Exes.driverDto.getId());

    }

    @Test
    void deleteDriverById() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(new Driver()));
        //when
        service.deleteDriverById(10);
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }

    @Test
    void findBySurname() {
        //given
        when(repository.findBySurnameContains(any())).thenReturn(Exes.driversList);
        //when
        List<DriverDto> result = service.findBySurname("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(Exes.driversList.size(),result.size());
    }

    @Test
    void findByEmailContains() {
        //given
        when(repository.findByEmailContains(any())).thenReturn(Exes.driversList);
        //when
        List<DriverDto> result = service.findByEmailContains("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(Exes.driversList.size(),result.size());
    }

    @Test
    void updateStatus() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(Exes.driver));
        when(repository.save(any(Driver.class))).thenReturn(null);
        //when
        service.updateStatus(1,DriverStatus.BREAK.toString());
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }


}
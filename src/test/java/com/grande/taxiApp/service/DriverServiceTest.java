package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.dto.DriverDto;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.exceptions.CarWithGivenPlatesException;
import com.grande.taxiapp.exceptions.DriverNotFoundException;
import com.grande.taxiapp.exceptions.EmailException;
import com.grande.taxiapp.mappers.CarMapper;
import com.grande.taxiapp.mappers.DriverMapper;
import com.grande.taxiapp.repository.CarRepository;
import com.grande.taxiapp.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
        DriverDto driver = new DriverDto(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
        //when
        service.saveDriver(driver);
        //then
        verify(repository,times(1)).save(any(Driver.class));

    }

    @Test
    void updateDriver() {
        //given
        DriverDto driver = new DriverDto(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
        when(mapper.mapToDriver(any(DriverDto.class))).thenReturn(new Driver());
        //when
        service.updateDriver(driver);
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }

    @Test
    void getAll() {
        //given
        List<Driver> drivers = Arrays.asList(new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test")),
                new Driver(2,"test","test","test","test", DriverStatus.ACTIVE,
                        new Car(2,"test","test","test","test")));
        when(repository.findAll()).thenReturn(drivers);
        //when
        List<Driver> result = service.getAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(drivers.size(),result.size());

    }

    @Test
    void findById() {
        //given
        Driver driver = new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
        when(repository.findById(any())).thenReturn(Optional.of(driver));
        //when
        Optional<Driver> result = service.findById(1);
        //then
        assertEquals(result.get().getId(),driver.getId());

    }

    @Test
    void deleteDriverById() {
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
        List<Driver> drivers = Arrays.asList(new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test")),
                new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                        new Car(1,"test","test","test","test")));
        when(repository.findBySurnameContains(any())).thenReturn(drivers);
        //when
        List<Driver> result = service.findBySurname("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(drivers.size(),result.size());
    }

    @Test
    void findByEmailContains() {
        //given
        List<Driver> drivers = Arrays.asList(new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                        new Car(1,"test","test","test","test")),
                new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                        new Car(1,"test","test","test","test")));
        when(repository.findByEmailContains(any())).thenReturn(drivers);
        //when
        List<Driver> result = service.findByEmailContains("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(drivers.size(),result.size());
    }

    @Test
    void updateStatus() throws DriverNotFoundException {
        //given
        Driver driver = new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
        when(repository.findById(any())).thenReturn(Optional.of(driver));
        when(repository.save(any(Driver.class))).thenReturn(null);
        //when
        service.updateStatus(1,DriverStatus.BREAK.toString());
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }
}
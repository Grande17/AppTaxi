package com.grande.taxiApp.service;

import com.grande.taxiApp.ResourceFactory;
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
        when(mapper.mapToDriver(any())).thenReturn(ResourceFactory.driver);
        when(repository.save(any())).thenReturn(ResourceFactory.driver);
        //when
        service.saveDriver(ResourceFactory.driverDto);
        //then
        verify(repository,times(1)).save(any(Driver.class));

    }

    @Test
    void updateDriver() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.ofNullable(ResourceFactory.driver));
        when(mapper.mapToDriver(any(DriverDto.class))).thenReturn(new Driver());
        //when
        service.updateDriver(ResourceFactory.driverDto);
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }

    @Test
    void getAll() {
        //given
        when(repository.findAll()).thenReturn(ResourceFactory.driversList);
        when(mapper.mapToListDto(any())).thenReturn(ResourceFactory.driversDtoList);
        //when
        List<DriverDto> result = service.getAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(ResourceFactory.driversDtoList.size(),result.size());

    }

    @Test
    void findById() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(ResourceFactory.driver));
        when(mapper.mapToDriverDto(any())).thenReturn(ResourceFactory.driverDto);
        //when
        Optional<DriverDto> result = Optional.ofNullable(service.findById(1));
        //then
        assertEquals(result.get().getId(), ResourceFactory.driverDto.getId());

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
        when(repository.findBySurnameContains(any())).thenReturn(ResourceFactory.driversList);
        when(mapper.mapToListDto(any())).thenReturn(ResourceFactory.driversDtoList);
        //when
        List<DriverDto> result = service.findBySurname("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(ResourceFactory.driversList.size(),result.size());
    }

    @Test
    void findByEmailContains() {
        //given
        when(repository.findByEmailContains(any())).thenReturn(ResourceFactory.driversList);
        when(mapper.mapToListDto(any())).thenReturn(ResourceFactory.driversDtoList);
        //when
        List<DriverDto> result = service.findByEmailContains("z");
        //then
        assertFalse(result.isEmpty());
        assertEquals(ResourceFactory.driversList.size(),result.size());
    }

    @Test
    void updateStatus() throws DriverNotFoundException {
        //given
        when(repository.findById(any())).thenReturn(Optional.of(ResourceFactory.driver));
        when(repository.save(any(Driver.class))).thenReturn(null);
        //when
        service.updateStatus(1,DriverStatus.BREAK.toString());
        //then
        verify(repository,times(1)).save(any(Driver.class));
    }


}
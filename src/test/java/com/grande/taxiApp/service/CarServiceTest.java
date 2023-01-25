package com.grande.taxiApp.service;

import com.grande.taxiApp.Exes;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.exceptions.CarNotFoundException;
import com.grande.taxiApp.mappers.CarMapper;
import com.grande.taxiApp.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.grande.taxiApp.Exes.car;
import static com.grande.taxiApp.Exes.carDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {


    private CarRepository carRepository = mock(CarRepository.class);
    private CarMapper mapper = mock(CarMapper.class);

    private CarService carService = mock(CarService.class);

    @Test
    void getCarById() throws CarNotFoundException {
        //given
        when(carService.getCarById(anyInt())).thenReturn(carDto);
        when(mapper.mapToCarDto(car)).thenReturn(carDto);
        when(carRepository.findById(anyInt())).thenReturn(Optional.ofNullable(Exes.car));
        //when
        CarDto get = carService.getCarById(1);
        //then
        assertEquals(carDto.getCarBrand(),get.getCarBrand());

    }

    @Test
    void findByPlatesContains() {
        when(carService.findByPlatesContains(anyString())).thenReturn(Exes.listCarDto);
        when(mapper.mapToCarDtoList(any())).thenReturn(Exes.listCarDto);
        when(carRepository.findByLicensePlateNumberContains(anyString())).thenReturn(Exes.carList);
        //when
        List<CarDto> result = carService.findByPlatesContains("t");
        //then
        assertEquals(Exes.listCarDto.size(),result.size());
    }

    @Test
    void findAll() {
        //given
        when(carService.findAll()).thenReturn(Exes.listCarDto);
        when(mapper.mapToCarDtoList(any())).thenReturn(Exes.listCarDto);
        when(carRepository.findAll()).thenReturn(Exes.carList);
        //when
        List<CarDto> result = carService.findAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(Exes.listCarDto.size(),result.size());
    }
    @Test
    void updateTest(){
        //given
        //when
        carService.updateCar(carDto);
        //then
        verify(carService,timeout(1)).updateCar(any(CarDto.class));
    }


}
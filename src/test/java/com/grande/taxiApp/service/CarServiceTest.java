package com.grande.taxiApp.service;

import com.grande.taxiApp.ResourceFactory;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.exceptions.CarNotFoundException;
import com.grande.taxiApp.mappers.CarMapper;
import com.grande.taxiApp.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.grande.taxiApp.ResourceFactory.car;
import static com.grande.taxiApp.ResourceFactory.carDto;
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
        when(carRepository.findById(anyInt())).thenReturn(Optional.ofNullable(ResourceFactory.car));
        //when
        CarDto get = carService.getCarById(1);
        //then
        assertEquals(carDto.getCarBrand(),get.getCarBrand());

    }

    @Test
    void findByPlatesContains() {
        when(carService.findByPlatesContains(anyString())).thenReturn(ResourceFactory.listCarDto);
        when(mapper.mapToCarDtoList(any())).thenReturn(ResourceFactory.listCarDto);
        when(carRepository.findByLicensePlateNumberContains(anyString())).thenReturn(ResourceFactory.carList);
        //when
        List<CarDto> result = carService.findByPlatesContains("t");
        //then
        assertEquals(ResourceFactory.listCarDto.size(),result.size());
    }

    @Test
    void findAll() {
        //given
        when(carService.findAll()).thenReturn(ResourceFactory.listCarDto);
        when(mapper.mapToCarDtoList(any())).thenReturn(ResourceFactory.listCarDto);
        when(carRepository.findAll()).thenReturn(ResourceFactory.carList);
        //when
        List<CarDto> result = carService.findAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(ResourceFactory.listCarDto.size(),result.size());
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
package com.grande.taxiApp.service;

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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper mapper;
    @InjectMocks
    private CarService carService;

    @Test
    void getCarById() throws CarNotFoundException {
        //given
        Car car = new Car("test","test","test","test");
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        //when
        Car get = carService.getCarById(1);
        //then
        assertEquals(car.getCarBrand(),get.getCarBrand());

    }

    @Test
    void findByPlatesContains() {
        //given
        Car car = new Car("test","test","test","test");
        when(carRepository.findByLicensePlateNumberContains("t")).thenReturn(List.of(car));
        //when
        List<Car> result = carService.findByPlatesContains("t");
        //then
        assertEquals(1,result.size());
    }

    @Test
    void findAll() {
        //given
        List<Car> cars = Arrays.asList(new Car("test","test","test","test"),
                new Car("test","test","test","test"));
        when(carRepository.findAll()).thenReturn(cars);
        //when
        List<Car> result = carService.findAll();
        //then
        assertFalse(result.isEmpty());
        assertEquals(cars.size(),result.size());
    }
    @Test
    void updateTest(){
        //given
        CarDto carDto = new CarDto(1,"test","test","test","test");
        Car car = new Car(1,"test","test","test","test");
        when(mapper.mapToCar(any(CarDto.class))).thenReturn(car);
        when(carRepository.save(any(Car.class))).thenReturn(null);
        //when
        carService.updateCar(carDto);
        //then
        verify(carRepository,timeout(1)).save(any(Car.class));
    }


}
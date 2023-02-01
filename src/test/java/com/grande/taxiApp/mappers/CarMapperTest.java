package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.grande.taxiApp.ResourceFactory.car;
import static com.grande.taxiApp.ResourceFactory.carDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CarMapperTest {

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CarMapper mapper;

    @Test
    void mapToCar() {
        //Given
        when(modelMapper.map(carDto,Car.class)).thenReturn(car);
        //When
        Car result = mapper.mapToCar(carDto);
        //Then

        assertEquals(car.getCarBrand(),result.getCarBrand());
        assertEquals(car.getModel(),result.getModel());
        assertEquals(car.getBodyType(),result.getBodyType());
        assertEquals(car.getLicensePlateNumber(),result.getLicensePlateNumber());
    }

    @Test
    void mapToCarDto() {
        //Given
        when(modelMapper.map(car,CarDto.class)).thenReturn(carDto);
        //When
        CarDto carDto = mapper.mapToCarDto(car);
        //Then
        assertEquals(car.getCarBrand(),carDto.getCarBrand());
        assertEquals(car.getModel(),carDto.getModel());
        assertEquals(car.getBodyType(),carDto.getBodyType());
        assertEquals(car.getLicensePlateNumber(),carDto.getLicensePlateNumber());

    }

    @Test
    void mapToCarDtoList() {
        //Given
        List<Car> cars = Arrays.asList(
                new Car("Audi","A4","Kombi","WWE12345"),
                new Car("BMW","M5","Sportback","WWE54321")
        );
        //When
        List<CarDto> dtos = mapper.mapToCarDtoList(cars);
        //Then
        assertEquals(cars.size(),dtos.size());
    }




}
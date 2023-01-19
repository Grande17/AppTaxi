package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarMapperTest {

    @Autowired
    private CarMapper mapper;

    @Test
    void mapToCar() {
        //Given
        CarDto carDto = new CarDto(1,"Audi","A4","Kombi","WWE12345");
        //When
        Car result = mapper.mapToCar(carDto);
        //Then
        assertEquals(1, result.getId());
        assertEquals("Audi",result.getCarBrand());
        assertEquals("A4",result.getModel());
        assertEquals("Kombi",result.getBodyType());
        assertEquals("WWE12345",result.getLicensePlateNumber());
    }

    @Test
    void mapToCarDto() {
        //Given
        Car car = new Car("Audi","A4","Kombi","WWE12345");
        //When
        CarDto carDto = mapper.mapToCarDto(car);
        //Then
        assertEquals(car.getId(),carDto.getId());
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
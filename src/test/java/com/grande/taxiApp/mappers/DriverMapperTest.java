package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.dto.DriverDto;
import com.grande.taxiApp.enums.DriverStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DriverMapperTest {
/*
    @Autowired
    private DriverMapper driverMapper;

    @Test
    void mapToDriver() {
        //Given
        DriverDto driverDto = new DriverDto(1,"Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                new Car("Brand","Model","BodyType","www9999"));
        //when
        Driver driver = driverMapper.mapToDriver(driverDto);
        //then
        assertEquals(driverDto.getId(),driver.getId());
    }

    @Test
    void mapToDriverDto() {
        //given
        Driver driver = new Driver("Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                new Car("Brand","Model","BodyType","www9999"));
        //when
        DriverDto driverDto = driverMapper.mapToDriverDto(driver);
        //then
        assertEquals(driver.getId(),driverDto.getId());
    }

    @Test
    void mapToListDto() {
        //given
        List<Driver> drivers = Arrays.asList(new Driver("Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                new Car("Brand","Model","BodyType","www9999")),
                new Driver("Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                        new Car("Brand","Model","BodyType","www9999")));
        //when
        List<DriverDto> dtos = driverMapper.mapToListDto(drivers);
        //then
        assertFalse(dtos.isEmpty());
        assertEquals(drivers.size(),dtos.size());
    }

 */
}
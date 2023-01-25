package com.grande.taxiApp.controllers;

import com.google.gson.Gson;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.mappers.CarMapper;
import com.grande.taxiApp.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {
/*
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;
    @MockBean
    private CarMapper carMapper;
    private CarDto carDto;
    private Car car;

    @BeforeEach
    void setUp() {
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        carDto = new CarDto(1,"test","test","test","test");
        car = new Car(1,"test","test","test","test");


    }

    @Test
    void updateCar() throws Exception {
        //given
        when(carMapper.mapToCar(any(CarDto.class))).thenReturn(car);
        doNothing().when(carService).updateCar(carDto);
        Gson gson = new Gson();
        String content = gson.toJson(carDto);
        //when & then

        mockMvc.perform(put("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content))
                .andExpect(status().isOk());


    }

    @Test
    void getAll() throws Exception {
        when(carMapper.mapToCarDtoList(any())).thenReturn(List.of(carDto));


        mockMvc.perform(get("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].carBrand",is("test")))
                .andExpect(jsonPath("$[0].model",is("test")))
                .andExpect(jsonPath("$[0].bodyType",is("test")))
                .andExpect(jsonPath("$[0].licensePlateNumber",is("test")));

    }

    @Test
    void getCarByPlates() throws Exception {
        when(carMapper.mapToCarDtoList(any())).thenReturn(List.of(carDto));
        when(carService.findByPlatesContains(any())).thenReturn(List.of(car));

        mockMvc.perform(get("/v1/cars/plates/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].carBrand",is("test")))
                .andExpect(jsonPath("$[0].model",is("test")))
                .andExpect(jsonPath("$[0].bodyType",is("test")))
                .andExpect(jsonPath("$[0].licensePlateNumber",is("test")));
    }

    @Test
    void findCarById() throws Exception {
        when(carMapper.mapToCarDto(any(Car.class))).thenReturn(carDto);
        when(carService.getCarById(any())).thenReturn(car);

        mockMvc.perform(get("/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.carBrand",is("test")))
                .andExpect(jsonPath("$.model",is("test")))
                .andExpect(jsonPath("$.bodyType",is("test")))
                .andExpect(jsonPath("$.licensePlateNumber",is("test")));

    }

 */
}
package com.grande.taxiApp.controllers;

import com.google.gson.Gson;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.dto.DriverDto;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.mappers.DriverMapper;
import com.grande.taxiApp.service.DriverService;
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
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriverController.class)
class DriverControllerTest {
/*
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private DriverService service;
    @MockBean
    private DriverMapper mapper;

    private Driver driver;
    private DriverDto driverDto;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        driver = new Driver(1,"test","test","test","test",
                new Car(1,"test","test","test","test"));
        driverDto = new DriverDto(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
    }

    @Test
    void createNewDriverAcc() throws Exception {
        when(service.saveDriver(any())).thenReturn(null);
        Gson gson = new Gson();
        String content = gson.toJson(driverDto);

        mockMvc.perform(post("/v1/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        when(mapper.mapToListDto(any())).thenReturn(List.of(driverDto));
        when(service.getAll()).thenReturn(List.of(driver));

        mockMvc.perform(get("/v1/drivers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].surname", is("test")))
                .andExpect(jsonPath("$[0].phoneNumber", is("test")))
                .andExpect(jsonPath("$[0].email", is("test")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")))
                .andExpect(jsonPath("$[0].car.id", is(1)))
                .andExpect(jsonPath("$[0].car.carBrand", is("test")));

    }

    @Test
    void getById() throws Exception {
        when(mapper.mapToDriverDto(any())).thenReturn(driverDto);
        when(service.findById(any())).thenReturn(Optional.ofNullable(driver));

        mockMvc.perform(get("/v1/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.surname", is("test")))
                .andExpect(jsonPath("$.phoneNumber", is("test")))
                .andExpect(jsonPath("$.email", is("test")))
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.car.id", is(1)))
                .andExpect(jsonPath("$.car.carBrand", is("test")));
    }

    @Test
    void updateDriver() throws Exception {
        when(service.saveDriver(any())).thenReturn(null);
        Gson gson = new Gson();
        String content = gson.toJson(driverDto);

        mockMvc.perform(put("/v1/drivers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDriverAcc() throws Exception {
        doNothing().when(service).deleteDriverById(any());

        mockMvc.perform(delete("/v1/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getByNameAndSurnameContains() throws Exception {
        when(mapper.mapToListDto(any())).thenReturn(List.of(driverDto));
        when(service.findBySurname(any())).thenReturn(List.of(driver));

        mockMvc.perform(get("/v1/drivers/name/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].surname", is("test")))
                .andExpect(jsonPath("$[0].phoneNumber", is("test")))
                .andExpect(jsonPath("$[0].email", is("test")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")))
                .andExpect(jsonPath("$[0].car.id", is(1)))
                .andExpect(jsonPath("$[0].car.carBrand", is("test")));
    }

    @Test
    void findByEmailContains() throws Exception {
        when(mapper.mapToListDto(any())).thenReturn(List.of(driverDto));
        when(service.findByEmailContains(any())).thenReturn(List.of(driver));

        mockMvc.perform(get("/v1/drivers/email/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].surname", is("test")))
                .andExpect(jsonPath("$[0].phoneNumber", is("test")))
                .andExpect(jsonPath("$[0].email", is("test")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")))
                .andExpect(jsonPath("$[0].car.id", is(1)))
                .andExpect(jsonPath("$[0].car.carBrand", is("test")));
    }

    @Test
    void updateStatus() throws Exception {
        when(service.updateStatus(any(),any())).thenReturn(null);

        mockMvc.perform(put("/v1/drivers/status/1/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

 */
}
package com.grande.taxiapp.controllers;

import com.google.gson.Gson;
import com.grande.taxiapp.domain.Car;
import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import com.grande.taxiapp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.enums.OrderTaxiStatus;
import com.grande.taxiapp.exceptions.NumberOfActiveOrdersException;
import com.grande.taxiapp.facade.OrderFacade;
import com.grande.taxiapp.mappers.OrderTaxiMapper;
import com.grande.taxiapp.service.OrderTaxiService;
import org.hamcrest.Matchers;
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

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderTaxiController.class)
class OrderTaxiControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private OrderTaxiService service;
    @MockBean
    private OrderTaxiMapper mapper;
    @MockBean
    private OrderFacade facade;
    private OrderTaxiDto orderTaxiDto;
    private OrderTaxi orderTaxi;
    private OrderTaxiFullDto orderTaxiFullDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        orderTaxi = new OrderTaxi(1,"test","test", new BigDecimal(99.9), LocalTime.of(1,19),
                new Customer(1,"test","test","test","test","test"),
                new Driver(1,"test","test","test","test",
                        new Car(1,"test","test","test","test")));
        orderTaxiDto = new OrderTaxiDto(1,"test","test",
                new Customer(1,"test","test","test","test","test"));
        orderTaxiFullDto = new OrderTaxiFullDto(1,"test","test",
                new Customer(1,"test","test","test","test","test"), new BigDecimal(99.9),
                LocalTime.of(1,19), OrderTaxiStatus.ACTIVE,
                new Driver(1,"test","test","test","test",new Car(1,"test","test","test","test")));
    }

    @Test
    void orderTaxi() throws Exception {
        when(facade.createOrder(orderTaxiDto)).thenReturn(null);
        when(mapper.mapToOrderTaxiFullDto(any())).thenReturn(orderTaxiFullDto);
        Gson gson = new Gson();
        String content = gson.toJson(orderTaxiDto);

        mockMvc.perform(post("/v1/order").
                contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.pickUpPlace", is("test")))
                .andExpect(jsonPath("$.dropPlace", is("test")))
                .andExpect(jsonPath("$.customer.id", is(1)))
                .andExpect(jsonPath("$.driver.id", is(1)));
    }

    @Test
    void cancel() throws Exception {
        doNothing().when(service).cancelOrderFouCustomersOnly(any());

        mockMvc.perform(put("/v1/order/cancel/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void returnUserOrderHistory() throws Exception {
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        when(service.findByCustomerId(any())).thenReturn(List.of(orderTaxi));

        mockMvc.perform(get("/v1/order/history/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pickUpPlace", is("test")))
                .andExpect(jsonPath("$[0].dropPlace", is("test")))
                .andExpect(jsonPath("$[0].customer.id", is(1)))
                .andExpect(jsonPath("$[0].driver.id", is(1)));
    }

    @Test
    void changeOrderStatus() throws Exception {
        doNothing().when(service).changeStatus(any(),any());

        mockMvc.perform(put("/v1/order/1/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        when(service.findAll()).thenReturn(List.of(orderTaxi));

        mockMvc.perform(get("/v1/order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pickUpPlace", is("test")))
                .andExpect(jsonPath("$[0].dropPlace", is("test")))
                .andExpect(jsonPath("$[0].customer.id", is(1)))
                .andExpect(jsonPath("$[0].driver.id", is(1)));
    }

    @Test
    void driverHistory() throws Exception {
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        when(service.findByDriverId(any())).thenReturn(List.of(orderTaxi));

        mockMvc.perform(get("/v1/order/driverHistory/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pickUpPlace", is("test")))
                .andExpect(jsonPath("$[0].dropPlace", is("test")))
                .andExpect(jsonPath("$[0].customer.id", is(1)))
                .andExpect(jsonPath("$[0].driver.id", is(1)));
    }

    @Test
    void findByStatus() throws Exception {
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        when(service.getByStatus(any())).thenReturn(List.of(orderTaxi));

        mockMvc.perform(get("/v1/order/status/ACTIVE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pickUpPlace", is("test")))
                .andExpect(jsonPath("$[0].dropPlace", is("test")))
                .andExpect(jsonPath("$[0].customer.id", is(1)))
                .andExpect(jsonPath("$[0].driver.id", is(1)));
    }
}
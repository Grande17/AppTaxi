package com.grande.taxiApp.controllers;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiDto;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.facade.OrderFacade;
import com.grande.taxiApp.foreignApi.addressToCoordinates.CoordinatesClient;
import com.grande.taxiApp.foreignApi.distanceAndTime.DistanceAndDurationClient;
import com.grande.taxiApp.mappers.OrderTaxiMapper;
import com.grande.taxiApp.repository.CustomerRepository;
import com.grande.taxiApp.repository.DriverRepository;
import com.grande.taxiApp.service.EmailService;
import com.grande.taxiApp.service.OrderTaxiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.*;
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

    /*
    TODO: fix date serializer
    @Test
    void orderTaxi() throws Exception {
        when(service.save(any())).thenReturn(orderTaxi);
        when(mapper.mapToOrderTaxiFullDto(any())).thenReturn(orderTaxiFullDto);
        when(facade.createOrder(orderTaxiDto)).thenReturn(orderTaxiFullDto);
        Gson gson = new Gson();
        String content = gson.toJson(orderTaxiFullDto);

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

     */

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
        when(service.findByCustomerId(any())).thenReturn(List.of(orderTaxiFullDto));

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
        when(service.findAll()).thenReturn(List.of(orderTaxiFullDto));

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
        when(service.findByDriverId(any())).thenReturn(List.of(orderTaxiFullDto));

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
        when(service.getByStatus(any())).thenReturn(List.of(orderTaxiFullDto));

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
package com.grande.taxiApp.controllers;

import com.google.gson.Gson;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.dto.CustomerDto;
import com.grande.taxiApp.mappers.CustomerMapper;
import com.grande.taxiApp.service.CustomerService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
/*
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private CustomerService service;
    @MockBean
    private CustomerMapper mapper;
    private Customer customer;
    private CustomerDto customerDto;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        customer = new Customer(1,"test","test","test","test","test");
        customerDto = new CustomerDto(1,"test","test","test","test","test");
    }

    @Test
    void createNewCustomerAcc() throws Exception {
        when(mapper.mapToCustomer(customerDto)).thenReturn(customer);
        when(service.saveCustomer(customer)).thenReturn(null);
        Gson gson = new Gson();
        String content = gson.toJson(customerDto);

        mockMvc.perform(post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());

    }

    @Test
    void updateCustomer() throws Exception {
        when(mapper.mapToCustomer(customerDto)).thenReturn(customer);
        when(service.saveCustomer(customer)).thenReturn(null);
        Gson gson = new Gson();
        String content = gson.toJson(customerDto);

        mockMvc.perform(put("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerById() throws Exception {
        when(mapper.mapToCustomerDto(customer)).thenReturn(customerDto);
        when(service.findCustomerById(any())).thenReturn(customer);

        mockMvc.perform(get("/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("test")))
                .andExpect(jsonPath("$.surname",is("test")))
                .andExpect(jsonPath("$.username",is("test")))
                .andExpect(jsonPath("$.phoneNumber",is("test")))
                .andExpect(jsonPath("$.email",is("test")));
    }

    @Test
    void getAll() throws Exception {
        when(mapper.mapToCustomerDtoList(any())).thenReturn(List.of(customerDto));
        when(service.findAll()).thenReturn(List.of(customer));

        mockMvc.perform(get("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("test")))
                .andExpect(jsonPath("$[0].surname",is("test")))
                .andExpect(jsonPath("$[0].username",is("test")))
                .andExpect(jsonPath("$[0].phoneNumber",is("test")))
                .andExpect(jsonPath("$[0].email",is("test")));
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(service).deleteCustomerById(any());

        mockMvc.perform(delete("/v1/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getByContains() throws Exception {
        when(mapper.mapToCustomerDtoList(any())).thenReturn(List.of(customerDto));
        when(service.findByUsername(any())).thenReturn(List.of(customer));

        mockMvc.perform(get("/v1/customers/username/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("test")))
                .andExpect(jsonPath("$[0].surname",is("test")))
                .andExpect(jsonPath("$[0].username",is("test")))
                .andExpect(jsonPath("$[0].phoneNumber",is("test")))
                .andExpect(jsonPath("$[0].email",is("test")));
    }

    @Test
    void getByName() throws Exception {
        when(mapper.mapToCustomerDtoList(any())).thenReturn(List.of(customerDto));
        when(service.findByName(any())).thenReturn(List.of(customer));

        mockMvc.perform(get("/v1/customers/name/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("test")))
                .andExpect(jsonPath("$[0].surname",is("test")))
                .andExpect(jsonPath("$[0].username",is("test")))
                .andExpect(jsonPath("$[0].phoneNumber",is("test")))
                .andExpect(jsonPath("$[0].email",is("test")));
    }

    @Test
    void updateEmail() throws Exception {
        doNothing().when(service).updateEmail(any(),any());

        mockMvc.perform(put("/v1/customers/email/1/email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

 */
}
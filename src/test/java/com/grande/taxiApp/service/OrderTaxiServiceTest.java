package com.grande.taxiApp.service;

import com.grande.taxiApp.ResourceFactory;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.exceptions.OrderTaxiNotFoundException;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
import com.grande.taxiApp.mappers.OrderTaxiMapper;
import com.grande.taxiApp.repository.DriverRepository;
import com.grande.taxiApp.repository.OrderTaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.grande.taxiApp.ResourceFactory.orderTaxi;
import static com.grande.taxiApp.ResourceFactory.orderTaxiFullDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderTaxiServiceTest {

    @Mock
    private OrderTaxiRepository orderTaxiRepository;
    @Mock
    private FuelPriceRepository fuelPriceRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private OrderTaxiMapper mapper;
    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private OrderTaxiService orderTaxiService;



    @Test
    void save() {
        //given
        when(orderTaxiRepository.save(any())).thenReturn(orderTaxi);
        //when
        orderTaxiService.save(orderTaxi);
        //then
        verify(orderTaxiRepository,times(1)).save(any(OrderTaxi.class));

    }


    @Test
    void findById() throws OrderTaxiNotFoundException {
        //given
        when(orderTaxiRepository.findById(any())).thenReturn(Optional.ofNullable(orderTaxi));
        when(mapper.mapToOrderTaxiFullDto(any())).thenReturn(orderTaxiFullDto);
        //when
        OrderTaxiFullDto find = orderTaxiService.findById(1);
        //then
        assertEquals(orderTaxi.getId(),find.getId());
    }

    @Test
    void findAll() {
        //given
        when(orderTaxiRepository.findAll()).thenReturn(List.of(orderTaxi));
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        //when
        List<OrderTaxiFullDto> all = orderTaxiService.findAll();
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());
    }

    @Test
    void findByCustomerId() {
        //given
        when(orderTaxiRepository.findByCustomerId(any())).thenReturn(List.of(orderTaxi));
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        //when
        List<OrderTaxiFullDto> all =orderTaxiService.findByCustomerId(1);
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());

    }

    @Test
    void findByDriverId() {
        //given
        when(orderTaxiRepository.findByDriverId(any())).thenReturn(List.of(orderTaxi));
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        //when
        List<OrderTaxiFullDto> all =orderTaxiService.findByDriverId(1);
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());
    }


    @Test
    void cancelOrderFouCustomersOnly() throws OrderTaxiNotFoundException {
        //given
        when(orderTaxiRepository.findById(any())).thenReturn(Optional.ofNullable(orderTaxi));
        when(driverRepository.save(any(Driver.class))).thenReturn(null);
        when(orderTaxiRepository.save(any(OrderTaxi.class))).thenReturn(null);
        //when
        orderTaxiService.cancelOrderFouCustomersOnly(1);
        //then
        verify(orderTaxiRepository,times(1)).save(any(OrderTaxi.class));
    }

    @Test
    void changeStatus() {
        //given
        when(orderTaxiRepository.findById(any())).thenReturn(Optional.ofNullable(orderTaxi));
        when(orderTaxiRepository.save(any(OrderTaxi.class))).thenReturn(null);

        //when
        orderTaxiService.changeStatus(1, "IN_PROGRESS");
        //then
        verify(orderTaxiRepository,times(1)).save(any(OrderTaxi.class));
    }

    @Test
    void getByStatus() {
        //given
        when(orderTaxiRepository.findByStatus(any(OrderTaxiStatus.class))).thenReturn(List.of(orderTaxi));
        when(mapper.mapToOrderTaxiFullDtoList(any())).thenReturn(List.of(orderTaxiFullDto));
        //when
        List<OrderTaxiFullDto> orders = orderTaxiService.getByStatus(OrderTaxiStatus.ACTIVE);
        //then
        assertFalse(orders.isEmpty());
        assertEquals(1,orders.size());
    }


}
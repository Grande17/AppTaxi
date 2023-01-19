package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
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
    private DriverRepository driverRepository;
    @InjectMocks
    private OrderTaxiService orderTaxiService;

    private Driver driver;
    private Customer customer;
    private OrderTaxi orderTaxi;

    @BeforeEach
    void setUp() {
        driver= new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                new Car(1,"test","test","test","test"));
        customer=new Customer(1,"test","test","test","test","test");
        orderTaxi = new OrderTaxi(1,"pickUp","drop",new BigDecimal(99.9), LocalTime.of(1,13),customer,driver);
    }

    @Test
    void save() {
        //when
        orderTaxiRepository.save(orderTaxi);
        //then
        verify(orderTaxiRepository,times(1)).save(any(OrderTaxi.class));

    }

    @Test
    void deleteById() {
        //when
        orderTaxiRepository.deleteById(1);
        //then
        verify(orderTaxiRepository,times(1)).deleteById(any());
    }

    @Test
    void findById() {
        //given
        when(orderTaxiRepository.findById(any())).thenReturn(Optional.ofNullable(orderTaxi));
        //when
        Optional<OrderTaxi> find = orderTaxiService.findById(1);
        //then
        assertEquals(orderTaxi.getId(),find.get().getId());
    }

    @Test
    void findAll() {
        //given
        when(orderTaxiRepository.findAll()).thenReturn(List.of(orderTaxi));
        //when
        List<OrderTaxi> all = orderTaxiService.findAll();
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());
    }

    @Test
    void findByCustomerId() {
        //given
        when(orderTaxiRepository.findByCustomerId(any())).thenReturn(List.of(orderTaxi));
        //when
        List<OrderTaxi> all =orderTaxiService.findByCustomerId(1);
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());

    }

    @Test
    void findByDriverId() {
        //given
        when(orderTaxiRepository.findByDriverId(any())).thenReturn(List.of(orderTaxi));
        //when
        List<OrderTaxi> all =orderTaxiService.findByDriverId(1);
        //then
        assertFalse(all.isEmpty());
        assertEquals(1,all.size());
    }


    @Test
    void cancelOrderFouCustomersOnly() {
        //given
        when(orderTaxiService.findById(any())).thenReturn(Optional.ofNullable(orderTaxi));
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
        //when
        List<OrderTaxi> orders = orderTaxiService.getByStatus(OrderTaxiStatus.ACTIVE);
        //then
        assertFalse(orders.isEmpty());
        assertEquals(1,orders.size());
    }
}
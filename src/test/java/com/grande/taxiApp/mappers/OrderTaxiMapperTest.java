package com.grande.taxiApp.mappers;

import com.grande.taxiApp.ResourceFactory;
import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.DriverStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.grande.taxiApp.ResourceFactory.orderTaxi;
import static com.grande.taxiApp.ResourceFactory.orderTaxiFullDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderTaxiMapperTest {

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private OrderTaxiMapper mapper;

    @Test
    void mapToOrderTaxiFullDto() {
        //given
        when(modelMapper.map(orderTaxi,OrderTaxiFullDto.class)).thenReturn(orderTaxiFullDto);
        //when
        OrderTaxiFullDto fullDto = mapper.mapToOrderTaxiFullDto(orderTaxi);
        //then
        assertEquals(orderTaxi.getId(),fullDto.getId());
        assertEquals(orderTaxi.getCustomer().getId(),fullDto.getCustomer().getId());
        assertEquals(orderTaxi.getDriver().getId(),fullDto.getDriver().getId());
    }

    @Test
    void mapToOrderTaxiFullDtoList() {
        //given
        List<OrderTaxi> orders = Arrays.asList(new OrderTaxi("PickUp","Drop",new BigDecimal(99.99), LocalTime.of(1,14),
                new Customer("name","surname","username","000999888","email"),
                new Driver("Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                        new Car("Brand","Model","BodyType","www9999"))),
                new OrderTaxi("PickUp","Drop",new BigDecimal(99.99), LocalTime.of(1,14),
                        new Customer("name","surname","username","000999888","email"),
                        new Driver("Name","Surname","000999888","email@email.com", DriverStatus.ACTIVE,
                                new Car("Brand","Model","BodyType","www9999"))));
        //when
        List<OrderTaxiFullDto> dtos = mapper.mapToOrderTaxiFullDtoList(orders);
        //then
        assertFalse(dtos.isEmpty());
        assertEquals(orders.size(),dtos.size());
    }


}
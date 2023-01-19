package com.grande.taxiApp.mappers;

import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderTaxiMapper {


    public OrderTaxiFullDto mapToOrderTaxiFullDto(final OrderTaxi orderTaxi){
        return new OrderTaxiFullDto(
                orderTaxi.getId(),
                orderTaxi.getPickUpPlace(),
                orderTaxi.getDropPlace(),
                orderTaxi.getCustomer(),
                orderTaxi.getEstimatedCost(),
                orderTaxi.getEstimatedTime(),
                orderTaxi.getStatus(),
                orderTaxi.getDriver()
        );
    }

    public List<OrderTaxiFullDto> mapToOrderTaxiFullDtoList(final List<OrderTaxi> orderTaxis){
        return orderTaxis.stream()
                .map(this::mapToOrderTaxiFullDto)
                .collect(Collectors.toList());
    }
}

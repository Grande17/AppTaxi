package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import com.grande.taxiapp.domain.dto.OrderTaxiFullDto;
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

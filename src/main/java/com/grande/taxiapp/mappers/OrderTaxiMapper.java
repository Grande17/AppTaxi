package com.grande.taxiapp.mappers;

import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderTaxiMapper {

    public OrderTaxi mapToOrderTaxi(final OrderTaxiDto orderTaxiDto){
        return new OrderTaxi(
                orderTaxiDto.getId(),
                orderTaxiDto.getPickUpPlace(),
                orderTaxiDto.getDropPlace(),
                orderTaxiDto.getCustomer());
    }
    public OrderTaxiDto mapToOrderTaxiDto(final OrderTaxi orderTaxi){
        return new OrderTaxiDto(
                orderTaxi.getId(),
                orderTaxi.getPickUpPlace(),
                orderTaxi.getDropPlace(),
                orderTaxi.getCustomer());
    }
    public List<OrderTaxiDto> mapToOrderTaxiDtoList(final List<OrderTaxi> orders){
        return orders.stream()
                .map(this::mapToOrderTaxiDto)
                .collect(Collectors.toList());
    }
}

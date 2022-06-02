package com.grande.taxiapp.controllers;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import com.grande.taxiapp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiapp.exceptions.CustomerNotFoundException;
import com.grande.taxiapp.exceptions.NumberOfActiveOrdersException;
import com.grande.taxiapp.facade.OrderFacade;
import com.grande.taxiapp.foreignAPI.addressToCoordinates.CoordinatesClient;
import com.grande.taxiapp.foreignAPI.distanceAndTime.DistanceAndDurationClient;
import com.grande.taxiapp.mappers.OrderTaxiMapper;
import com.grande.taxiapp.service.CustomerService;
import com.grande.taxiapp.service.OrderTaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderTaxiController {

    private final OrderTaxiService orderTaxiService;
    private final OrderTaxiMapper orderTaxiMapper;
    private final CoordinatesClient coordinatesClient;
    private final DistanceAndDurationClient distanceAndDurationClient;
    private final CustomerService customerService;
    private final OrderFacade facade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderTaxiFullDto orderTaxi(@RequestBody OrderTaxiDto orderTaxiDto) throws NumberOfActiveOrdersException {
        OrderTaxi order = facade.createOrder(orderTaxiDto);
        return orderTaxiMapper.mapToOrderTaxiFullDto(order);
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<Void> cancel (@PathVariable Integer id){
        orderTaxiService.cancelOrderFouCustomersOnly(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "history/{id}")
    public ResponseEntity<List<OrderTaxiFullDto>> returnUserOrderHistory(@PathVariable Integer id){
        List<OrderTaxi> list = orderTaxiService.findByCustomerId(id);
        return ResponseEntity.ok(orderTaxiMapper.mapToOrderTaxiFullDtoList(list));
    }
    @GetMapping("/get")
    public OrderTaxiDto get() {
        return new OrderTaxiDto(1,"Pickup", "drop",new Customer(1,"xx","xx","xx","xx","xx","xx"));
    }

}

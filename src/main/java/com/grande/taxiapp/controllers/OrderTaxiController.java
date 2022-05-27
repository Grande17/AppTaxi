package com.grande.taxiapp.controllers;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import com.grande.taxiapp.mappers.OrderTaxiMapper;
import com.grande.taxiapp.service.OrderTaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderTaxiController {

    private final OrderTaxiService orderTaxiService;
    private final OrderTaxiMapper orderTaxiMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> orderTaxi(@RequestBody OrderTaxiDto orderTaxiDto){
        OrderTaxi orderTaxi = orderTaxiMapper.mapToOrderTaxi(orderTaxiDto);
        orderTaxiService.save(orderTaxi);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public OrderTaxiDto get(){
        return new OrderTaxiDto(1,"Kraków","Lublin",new Customer(
                1,"Adam","Adamski","adamiak","999888777","Mail@mail.com"
        ));
    }
}

package com.grande.taxiApp.controllers;

import com.grande.taxiApp.domain.dto.OrderTaxiDto;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.exceptions.NumberOfActiveOrdersException;
import com.grande.taxiApp.exceptions.OrderTaxiNotFoundException;
import com.grande.taxiApp.facade.OrderFacade;
import com.grande.taxiApp.service.OrderTaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
@Validated
public class OrderTaxiController {

    private final OrderTaxiService orderTaxiService;
    private final OrderFacade facade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderTaxiFullDto orderTaxi(@RequestBody @Valid OrderTaxiDto orderTaxiDto) throws Exception {
        return facade.createOrder(orderTaxiDto);

    }
    @PutMapping(value = "cancel/{id}")
    public ResponseEntity<Void> cancel (@PathVariable Integer id) throws OrderTaxiNotFoundException {
        orderTaxiService.cancelOrderFouCustomersOnly(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "history/{id}")
    public ResponseEntity<List<OrderTaxiFullDto>> returnUserOrderHistory(@PathVariable Integer id){
        return ResponseEntity.ok(orderTaxiService.findByCustomerId(id));
    }
    @PutMapping(value = "{orderId}/{status}")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Integer orderId, @PathVariable String status){
        orderTaxiService.changeStatus(orderId,status);
        return ResponseEntity.ok().build();

    }
    @GetMapping
    public ResponseEntity<List<OrderTaxiFullDto>> getAll(){
        return ResponseEntity.ok(orderTaxiService.findAll());
    }
    @GetMapping(value = "driverHistory/{id}")
    public ResponseEntity<List<OrderTaxiFullDto>> driverHistory(@PathVariable Integer id){
        return ResponseEntity.ok(orderTaxiService.findByDriverId(id));
    }
    @GetMapping(value = "status/{status}")
    public ResponseEntity<List<OrderTaxiFullDto>> findByStatus(@PathVariable OrderTaxiStatus status){
        return ResponseEntity.ok(orderTaxiService.getByStatus(status));
    }

}

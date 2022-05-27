package com.grande.taxiapp.service;

import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.repository.OrderTaxiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderTaxiService {

    private final OrderTaxiRepository orderTaxiRepository;

    public OrderTaxi save(OrderTaxi orderTaxi){
        return orderTaxiRepository.save(orderTaxi);
    }
    public void deleteById(Integer id){
        orderTaxiRepository.deleteById(id);
    }
    public Optional<OrderTaxi> findById(Integer id){
        return orderTaxiRepository.findById(id);
    }
    public List<OrderTaxi> findAll(){
        return orderTaxiRepository.findAll();
    }
}

package com.grande.taxiApp.repository;

import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderTaxiRepository extends JpaRepository<OrderTaxi, Integer> {

    List<OrderTaxi> findAll();

    Optional<OrderTaxi> findById(Integer id);



    List<OrderTaxi> findByStatus(OrderTaxiStatus status);

    List<OrderTaxi> findByCustomerId(Integer customerID);

    List<OrderTaxi> findByDriverId(Integer id);

    OrderTaxi save(OrderTaxi orderTaxi);

    void deleteById(Integer id);

}

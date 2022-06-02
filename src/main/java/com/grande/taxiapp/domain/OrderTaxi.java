package com.grande.taxiapp.domain;

import com.grande.taxiapp.enums.OrderTaxiStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "TAXI_ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class OrderTaxi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String pickUpPlace;
    @NotNull
    private String dropPlace;
    private BigDecimal estimatedCost;
    private LocalTime estimatedTime;

    @Enumerated(EnumType.STRING)
    private OrderTaxiStatus status;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Driver driver;

    public OrderTaxi(Integer id, String pickUpPlace, String dropPlace, Customer customer) {
        this.id = id;
        this.pickUpPlace = pickUpPlace;
        this.dropPlace = dropPlace;
        this.customer = customer;
    }

    public OrderTaxi(Integer id, String pickUpPlace,
                     String dropPlace, BigDecimal estimatedCost,
                     LocalTime estimatedTime,
                     Customer customer, Driver driver) {
        this.id = id;
        this.pickUpPlace = pickUpPlace;
        this.dropPlace = dropPlace;
        this.estimatedCost = estimatedCost;
        this.estimatedTime = estimatedTime;
        this.status = OrderTaxiStatus.ACTIVE;
        this.customer = customer;
        this.driver = driver;
    }

}

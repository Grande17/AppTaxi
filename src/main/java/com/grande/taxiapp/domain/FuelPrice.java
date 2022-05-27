package com.grande.taxiapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "FUEL_PRICE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class FuelPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String currency;
    private String country;
    private BigDecimal price;
    private LocalDateTime lastUpdate;


    public FuelPrice(String currency, String country, BigDecimal price, LocalDateTime lastUpdate) {
        this.currency = currency;
        this.country = country;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

}

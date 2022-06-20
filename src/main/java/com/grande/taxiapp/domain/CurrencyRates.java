package com.grande.taxiapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "CURRENCY_RATES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class CurrencyRates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String currency;
    private BigDecimal price;
    private LocalDateTime lastUpdate;

    public CurrencyRates(String currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
        this.lastUpdate = LocalDateTime.now();
    }

}

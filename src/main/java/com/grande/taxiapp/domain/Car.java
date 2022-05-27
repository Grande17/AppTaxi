package com.grande.taxiapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity(name = "CARS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "BRAND")
    private String carBrand;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "TYPE")
    private String bodyType;
    @Column(name = "PLATE_NUMBER")
    private String licensePlateNumber;


    public Car(String carBrand, String model, String bodyType, String licensePlateNumber) {
        this.carBrand = carBrand;
        this.model = model;
        this.bodyType = bodyType;
        this.licensePlateNumber = licensePlateNumber;
    }
}

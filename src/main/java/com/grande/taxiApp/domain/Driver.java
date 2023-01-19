package com.grande.taxiApp.domain;

import com.grande.taxiApp.enums.DriverStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "DRIVERS")
@NoArgsConstructor
@Getter
@Setter
@Table
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @Enumerated(value = EnumType.STRING)
    private DriverStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    private Car car;


    public Driver(Integer id, String name, String surname, String phoneNumber, String email, Car car) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = DriverStatus.ACTIVE;
        this.car = car;
    }
    public Driver(Integer id, String name, String surname, String phoneNumber, String email,DriverStatus status, Car car) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.car = car;
    }
    public Driver( String name, String surname, String phoneNumber, String email,DriverStatus status, Car car) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.car = car;
    }
}

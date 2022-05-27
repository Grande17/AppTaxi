package com.grande.taxiapp.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "CUSTOMERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Column(name = "SURNAME")
    private String surname;
    @NotNull
    @Column(name = "LOGIN")
    private String username;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @NotNull
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderTaxi> orders;

    public Customer(Integer id, String name, String surname, String username, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

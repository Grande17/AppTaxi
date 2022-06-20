package com.grande.taxiapp.domain;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

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
    @Nullable
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @NotNull
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DISCOUNT")
    private Double discount = 1.0;

    public static class Builder{
        private Integer id;
        private String name;
        private String surname;
        private String username;
        private String phoneNumber;
        private String email;
        private Double discount = 1.0;

        public Builder id(Integer id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder surname(String surname){
            this.surname = surname;
            return this;
        }
        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Customer build(){
            return new Customer(id, name,surname,username,phoneNumber,email,discount);
        }

    }

    public Customer(Integer id, String name, String surname, String username, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.discount = 1.0;
    }
    public Customer(String name, String surname, String username, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.discount = 1.0;
    }
}

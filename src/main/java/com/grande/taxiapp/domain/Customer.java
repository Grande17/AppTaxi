package com.grande.taxiapp.domain;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @Nullable
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @NotNull
    @Column(name = "EMAIL")
    private String email;


    public static class Builder{
        private Integer id;
        private String name;
        private String surname;
        private String username;
        private String password;
        private String phoneNumber;
        private String email;

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
        public Builder password(String password){
            this.password = password;
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
            return new Customer(id, name,surname,username,password,phoneNumber,email);
        }

    }


}

package com.grande.taxiapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String phoneNumber;
    private String email;


}

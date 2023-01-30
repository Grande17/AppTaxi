package com.grande.taxiApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

package com.grande.taxiApp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {

    private String mailTo;
    private String subject;
    private String message;
}

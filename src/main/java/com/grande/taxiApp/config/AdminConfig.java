package com.grande.taxiApp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AdminConfig {

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private Integer emailPort;
    @Value("${spring.mail.username}")
    private String mailLogin;
    @Value("${spring.mail.password}")
    private String mailPassword;

}

package com.grande.taxiApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private final AdminConfig adminConfig;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(adminConfig.getMailHost());
        mailSender.setPort(adminConfig.getEmailPort());

        mailSender.setUsername(adminConfig.getMailLogin());
        mailSender.setPassword(adminConfig.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "true");


        return mailSender;
    }
}

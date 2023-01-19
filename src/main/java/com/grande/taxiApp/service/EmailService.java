package com.grande.taxiApp.service;

import com.grande.taxiApp.domain.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;

    private SimpleMailMessage createMessage(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getMailTo());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getMessage());
        return message;
    }

    public void send(MailDto mailDto){
        try{
            SimpleMailMessage simpleMailMessage = createMessage(mailDto);
            javaMailSender.send(simpleMailMessage);
            log.info("Email has been sent.");
        }catch (MailException e){
            log.error("Failed to send an email: "+e.getMessage());
        }
    }
}

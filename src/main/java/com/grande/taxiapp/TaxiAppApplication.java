package com.grande.taxiapp;

import com.grande.taxiapp.scheduler.ScheduledEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiAppApplication.class, args);

    }

}

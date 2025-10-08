package com.example.smarthomeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // YENİ IMPORT

@SpringBootApplication
public class SmartHomeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApiApplication.class, args);
    }

}
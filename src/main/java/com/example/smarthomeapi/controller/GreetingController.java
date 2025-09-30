package com.example.smarthomeapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Merhaba, Akıllı Ev Projesine Hoş Geldin!";
    }
}
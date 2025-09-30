package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device; // EKLENDİ
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    // "/hello" endpoint'ini güncelleyerek "/sample-device" yapalım
    @GetMapping("/sample-device")
    public Device getSampleDevice() { // DÖNÜŞ TİPİ DEĞİŞTİ
        // Yeni bir Device nesnesi oluşturup, içini test verileriyle dolduralım
        return new Device(1L, "Masa Lambası", true); // DÖNEN DEĞER DEĞİŞTİ
    }
}
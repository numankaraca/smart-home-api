package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.service.DeviceService; // EKLENDİ
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; // EKLENDİ

@RestController
@RequestMapping("/api/v1/devices") // ORTAK YOL DEĞİŞTİ
public class DeviceController {

    private final DeviceService deviceService;

    // Dependency Injection: Spring, DeviceService nesnesini otomatik olarak buraya enjekte eder.
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }
}
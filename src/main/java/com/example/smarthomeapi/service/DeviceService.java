package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    // Şimdilik veritabanı yerine sabit bir liste oluşturalım
    private final List<Device> deviceList = List.of(
            new Device(1L, "Salon Lambası", true),
            new Device(2L, "Mutfak Prizi", false),
            new Device(3L, "Yatak Odası Sensörü", true)
    );

    public List<Device> getAllDevices() {
        return deviceList;
    }
}
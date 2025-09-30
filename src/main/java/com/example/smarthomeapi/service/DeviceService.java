package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.Device;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceService {

    private final List<Device> deviceList = List.of(
            new Device(1L, "Salon Lambası", true),
            new Device(2L, "Mutfak Prizi", false),
            new Device(3L, "Yatak Odası Sensörü", true)
    );

    public List<Device> getAllDevices() {
        return deviceList;
    }

    // YENİ EKLENEN METOD
    public Device getDeviceById(Long id) {
        return deviceList.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
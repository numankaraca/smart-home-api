package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // --- Sadece bu metodlar kaldı ---

    // ID'si bilinen tek bir cihazı, sadece sahibi ise getirir.
    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    // ID'si bilinen tek bir cihazı, sadece sahibi ise günceller.
    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id, @Valid @RequestBody Device deviceDetails) {
        return deviceService.updateDevice(id, deviceDetails);
    }

    // ID'si bilinen tek bir cihazı, sadece sahibi ise siler.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    // GET / (getAllDevices) metodu buradan SİLİNDİ.
    // GET /search metodu buradan SİLİNDİ.
}
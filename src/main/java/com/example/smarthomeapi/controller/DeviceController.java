package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // Bu metodda değişiklik yok, zaten doğru çalışıyordu.
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    // --- GÜNCELLENDİ ---
    // Artık ResponseEntity döndürmüyor, doğrudan Device döndürüyor.
    // 'if/else' kontrolü tamamen kalktı!
    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    // Bu metodda değişiklik yok.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device createDevice(@Valid @RequestBody Device device) {
        return deviceService.addDevice(device);
    }

    // --- GÜNCELLENDİ ---
    // Bu metod da artık doğrudan Device döndürüyor.
    // 'if/else' kontrolü tamamen kalktı!
    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id, @Valid @RequestBody Device deviceDetails) {
        return deviceService.updateDevice(id, deviceDetails);
    }

    // --- GÜNCELLENDİ ---
    // 'if/else' kontrolü kalktı. Metod artık daha basit.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    // Bu metodda değişiklik yok.
    @GetMapping("/search")
    public List<Device> searchDevices(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String name) {

        if (name != null) {
            return deviceService.searchDevicesByName(name);
        } else if (status != null) {
            return deviceService.getDevicesByStatus(status);
        } else {
            return deviceService.getAllDevices();
        }
    }
}
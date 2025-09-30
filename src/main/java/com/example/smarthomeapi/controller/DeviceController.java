package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) { // DÖNÜŞ TİPİ DEĞİŞTİ
        Optional<Device> deviceOptional = deviceService.getDeviceById(id);

        if (deviceOptional.isPresent()) {
            // Optional içinde bir device varsa, onu 200 OK statusu ile döndür
            return ResponseEntity.ok(deviceOptional.get());
        } else {
            // Optional boş ise, 404 Not Found statusu ile boş bir cevap döndür
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device createDevice(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
        Optional<Device> updatedDeviceOptional = deviceService.updateDevice(id, deviceDetails);

        if (updatedDeviceOptional.isPresent()) {
            return ResponseEntity.ok(updatedDeviceOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


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
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    // @PostMapping METODU BURADAN SİLİNDİ!

    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id, @Valid @RequestBody Device deviceDetails) {
        return deviceService.updateDevice(id, deviceDetails);
    }

    // YENİ VE DOĞRU HALİ:
    @Operation(summary = "Delete a device by its ID")
    @ApiResponse(responseCode = "204", description = "Device deleted successfully")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // BU SATIRI EKLE
    public void deleteDevice(@PathVariable Long id) { // DÖNÜŞ TİPİNİ void YAP
        deviceService.deleteDevice(id);
        // return satırı tamamen kalktı!
    }

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
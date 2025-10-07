package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long id) {
        // Repository'den cihazı bul, bulamazsa ResourceNotFoundException fırlat.
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
    }

    public Device addDevice(Device newDevice) {
        return deviceRepository.save(newDevice);
    }

    public Device updateDevice(Long id, Device deviceDetails) {
        // Önce güncellenecek cihazın var olup olmadığını kontrol et.
        // Bulamazsa, orElseThrow metodu bizim için hatayı fırlatacak.
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));

        // Cihaz bulunduysa, alanlarını güncelle ve veritabanına kaydet.
        existingDevice.setName(deviceDetails.getName());
        existingDevice.setStatus(deviceDetails.isStatus());
        return deviceRepository.save(existingDevice);
    }

    public void deleteDevice(Long id) {
        // Silinecek cihazın var olup olmadığını kontrol et.
        // Eğer yoksa, hata fırlat.
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
        // Cihaz varsa, sil.
        deviceRepository.deleteById(id);
    }

    // Arama metodları aynı kalıyor.
    public List<Device> getDevicesByStatus(boolean status) {
        return deviceRepository.findByStatus(status);
    }

    public List<Device> searchDevicesByName(String keyword) {
        return deviceRepository.findByNameContainingIgnoreCase(keyword);
    }
}
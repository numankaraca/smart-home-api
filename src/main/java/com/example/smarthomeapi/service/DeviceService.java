package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.repository.DeviceRepository; // YENİ IMPORT
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    // Artık ArrayList veya sayaç yok! Onun yerine Repository'miz var.
    private final DeviceRepository deviceRepository;

    // Dependency Injection ile Spring'in DeviceRepository'yi bize vermesini sağlıyoruz.
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        // Artık doğrudan veritabanından tüm cihazları çekiyoruz.
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        // Repository'nin hazır findById metodu zaten Optional döndürüyor.
        return deviceRepository.findById(id);
    }

    public Device addDevice(Device newDevice) {
        // Repository'nin save metodu, yeni bir nesneyi veritabanına ekler (INSERT).
        return deviceRepository.save(newDevice);
    }

    public Optional<Device> updateDevice(Long id, Device deviceDetails) {
        // Önce cihazın veritabanında olup olmadığını kontrol edelim.
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isPresent()) {
            Device existingDevice = deviceOptional.get();
            existingDevice.setName(deviceDetails.getName());
            existingDevice.setStatus(deviceDetails.isStatus());
            // Repository'nin save metodu, ID'si olan bir nesneyi günceller (UPDATE).
            return Optional.of(deviceRepository.save(existingDevice));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteDevice(Long id) {
        // Cihazın var olup olmadığını kontrol edelim.
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // DeviceService.java içine eklenecek yeni metod

    public List<Device> getDevicesByStatus(boolean status) {
        return deviceRepository.findByStatus(status);
    }

    public List<Device> searchDevicesByName(String keyword) {
        return deviceRepository.findByNameContainingIgnoreCase(keyword);
    }

}
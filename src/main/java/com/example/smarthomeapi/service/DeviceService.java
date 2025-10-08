package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.model.Room; // YENİ IMPORT
import com.example.smarthomeapi.repository.DeviceRepository;
import com.example.smarthomeapi.repository.RoomRepository; // YENİ IMPORT
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository; // YENİ ALAN

    // Constructor'ı iki repository'yi de alacak şekilde güncelliyoruz.
    public DeviceService(DeviceRepository deviceRepository, RoomRepository roomRepository) {
        this.deviceRepository = deviceRepository;
        this.roomRepository = roomRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
    }

    // --- ESKİ addDevice METODUNU SİLİP YERİNE BUNU EKLİYORUZ ---
    public Device addDeviceToRoom(Long roomId, Device newDevice) {
        // 1. Cihazın ekleneceği odayı veritabanından bul.
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        // 2. Yeni cihaza ait olduğu odayı ata.
        newDevice.setRoom(room);

        // 3. Odaya bağlanmış olan yeni cihazı kaydet.
        return deviceRepository.save(newDevice);
    }
    // -----------------------------------------------------------

    public Device updateDevice(Long id, Device deviceDetails) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));

        existingDevice.setName(deviceDetails.getName());
        existingDevice.setStatus(deviceDetails.isStatus());
        return deviceRepository.save(existingDevice);
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
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
package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.Device;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class DeviceService {

    private final List<Device> deviceList = new ArrayList<>(List.of(
            new Device(1L, "Salon Lambası", true),
            new Device(2L, "Mutfak Prizi", false),
            new Device(3L, "Yatak Odası Sensörü", true)
    ));

    private final AtomicLong counter = new AtomicLong(deviceList.size());

    public List<Device> getAllDevices() {
        return deviceList;
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceList.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst();
    }

    public Device addDevice(Device newDevice) {
        // Yeni cihaza benzersiz bir ID ata
        Long newId = counter.incrementAndGet();
        newDevice.setId(newId);

        // Cihazı listeye ekle
        deviceList.add(newDevice);

        // Eklenen cihazı geri döndür
        return newDevice;
    }

    public Optional<Device> updateDevice(Long id, Device deviceDetails) {
        // Önce güncellenecek cihazı ID'sine göre bulalım.
        Optional<Device> deviceOptional = getDeviceById(id);

        // Eğer cihaz bulunduysa...
        if (deviceOptional.isPresent()) {
            Device existingDevice = deviceOptional.get();

            // Mevcut cihazın bilgilerini yeni gelenlerle güncelle.
            existingDevice.setName(deviceDetails.getName());
            existingDevice.setStatus(deviceDetails.isStatus());

            // Güncellenmiş cihazı Optional içinde geri döndür.
            return Optional.of(existingDevice);
        } else {
            // Cihaz bulunamadıysa, boş bir Optional döndür.
            return Optional.empty();
        }
    }

}
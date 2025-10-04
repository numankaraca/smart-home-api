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
        Long newId = counter.incrementAndGet();
        newDevice.setId(newId);

        deviceList.add(newDevice);

        return newDevice;
    }

    public Optional<Device> updateDevice(Long id, Device deviceDetails) {

        Optional<Device> deviceOptional = getDeviceById(id);

        if (deviceOptional.isPresent()) {
            Device existingDevice = deviceOptional.get();

            existingDevice.setName(deviceDetails.getName());
            existingDevice.setStatus(deviceDetails.isStatus());


            return Optional.of(existingDevice);
        } else {

            return Optional.empty();
        }
    }


    public boolean deleteDevice(Long id) {
        return deviceList.removeIf(device -> device.getId().equals(id));
    }

}
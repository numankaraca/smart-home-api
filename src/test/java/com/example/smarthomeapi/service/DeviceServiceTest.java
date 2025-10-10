package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional; // Gerekli import

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void testGetAllDevices() {
        // GIVEN
        Device device1 = new Device(1L, "Lamba", true, null);
        Device device2 = new Device(2L, "Priz", false, null);
        List<Device> fakeDeviceList = List.of(device1, device2);
        given(deviceRepository.findAll()).willReturn(fakeDeviceList);

        // WHEN
        List<Device> result = deviceService.getAllDevices();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getDeviceById_whenDeviceExists_shouldReturnDevice() {
        // GIVEN
        final Long deviceId = 1L;
        Device fakeDevice = new Device(deviceId, "Test Lambası", true, null);
        given(deviceRepository.findById(deviceId)).willReturn(Optional.of(fakeDevice));

        // WHEN
        Device result = deviceService.getDeviceById(deviceId);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(deviceId);
        assertThat(result.getName()).isEqualTo("Test Lambası");
    }

    @Test
    void getDeviceById_whenDeviceDoesNotExist_shouldThrowException() {
        // GIVEN
        final Long nonExistentId = 99L;
        given(deviceRepository.findById(nonExistentId)).willReturn(Optional.empty());

        // WHEN & THEN
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            deviceService.getDeviceById(nonExistentId);
        });

        assertThat(exception.getMessage()).isEqualTo("Device not found with id: " + nonExistentId);
    }
}
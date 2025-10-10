package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.repository.DeviceRepository;
import com.example.smarthomeapi.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private RoomRepository roomRepository; // Yeni mock

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

    @Test
    void addDeviceToRoom_whenRoomExists_shouldSaveAndReturnDevice() {
        // GIVEN (Hazırlık)
        Long roomId = 1L;
        Room fakeRoom = new Room(roomId, "Mutfak", null);
        Device newDevice = new Device(null, "Yeni Lamba", false, null); // ID'si henüz yok
        Device savedDevice = new Device(4L, "Yeni Lamba", false, fakeRoom); // Kaydedilmiş halinin ID'si var

        // Sahte repository'lere talimatlarımızı veriyoruz:
        // 1. "roomRepository.findById(1L) çağrıldığında, sahte odamızı döndür."
        given(roomRepository.findById(roomId)).willReturn(Optional.of(fakeRoom));
        // 2. "deviceRepository.save() metodu herhangi bir Device nesnesi ile çağrıldığında, 'savedDevice' nesnesini döndür."
        given(deviceRepository.save(any(Device.class))).willReturn(savedDevice);

        // WHEN (Aksiyon)
        // Asıl test edeceğimiz metodu çağırıyoruz.
        Device result = deviceService.addDeviceToRoom(roomId, newDevice);

        // THEN (Doğrulama)
        // 1. Dönen sonucun beklediğimiz gibi olup olmadığını kontrol et.
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(4L);

        // 2. Metodların doğru çağrılıp çağrılmadığını DOĞRULA (verify).
        // "roomRepository'nin findById metodu, 1L argümanıyla tam 1 kez çağrıldı mı?"
        verify(roomRepository).findById(roomId);
        // "deviceRepository'nin save metodu, herhangi bir Device nesnesiyle tam 1 kez çağrıldı mı?"
        verify(deviceRepository).save(any(Device.class));
    }
}
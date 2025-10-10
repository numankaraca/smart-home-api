package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository; // Bu, bizim sahte Repository'miz

    @InjectMocks
    private DeviceService deviceService; // Bu, içine sahte repository'nin enjekte edileceği, test edeceğimiz asıl servis

    @Test
    void testGetAllDevices() {
        // GIVEN (Hazırlık Aşaması)
        // Sahte bir cihaz listesi oluşturalım.
        Device device1 = new Device(1L, "Lamba", true, null);
        Device device2 = new Device(2L, "Priz", false, null);
        List<Device> fakeDeviceList = List.of(device1, device2);

        // Sahte repository'ye talimat veriyoruz:
        // "Ne zaman birisi senin findAll() metodunu çağırırsa, ona fakeDeviceList'i geri döndür."
        given(deviceRepository.findAll()).willReturn(fakeDeviceList);

        // WHEN (Aksiyon Aşaması)
        // Test etmek istediğimiz asıl metodu çağırıyoruz.
        List<Device> result = deviceService.getAllDevices();

        // THEN (Doğrulama Aşaması)
        // Gelen sonucun beklediğimiz gibi olup olmadığını kontrol ediyoruz.
        assertThat(result).isNotNull(); // Sonucun null olmadığını doğrula
        assertThat(result.size()).isEqualTo(2); // Sonuç listesinin 2 elemanlı olduğunu doğrula
        assertThat(result).isEqualTo(fakeDeviceList); // Sonucun, sahte listemizle aynı olduğunu doğrula
    }
}
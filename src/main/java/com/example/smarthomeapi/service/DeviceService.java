package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.repository.DeviceRepository;
import com.example.smarthomeapi.repository.RoomRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;

    public DeviceService(DeviceRepository deviceRepository, RoomRepository roomRepository) {
        this.deviceRepository = deviceRepository;
        this.roomRepository = roomRepository;
    }

    // --- YARDIMCI METOD ---
    // Spring Security'nin kasasından o anki kullanıcıyı getiren metod.
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // --- GÜVENLİ HALE GETİRİLMİŞ METODLAR ---

    // Bir cihazı, sadece o anki kullanıcıya aitse ID'sine göre getirir.
    public Device getDeviceById(Long deviceId) {
        User currentUser = getCurrentUser();
        // Repository'den cihazı, hem kendi ID'sine hem de sahibinin ID'sine göre arar.
        return deviceRepository.findByIdAndRoomUser(deviceId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + deviceId));
    }

    // Bir cihazi, sadece o anki kullanıcıya ait bir odaya ekler.
    public Device addDeviceToRoom(Long roomId, Device newDevice) {
        User currentUser = getCurrentUser();
        // Önce odanın bu kullanıcıya ait olup olmadığını kontrol et.
        Room room = roomRepository.findByIdAndUser(roomId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        newDevice.setRoom(room);
        return deviceRepository.save(newDevice);
    }

    // Bir cihazı, sadece o anki kullanıcıya aitse günceller.
    public Device updateDevice(Long deviceId, Device deviceDetails) {
        // getDeviceById metodu zaten sahip kontrolü yapıyor.
        // Eğer cihaz bu kullanıcıya ait değilse, aşağıdaki satır hata fırlatacak.
        Device existingDevice = getDeviceById(deviceId);

        existingDevice.setName(deviceDetails.getName());
        existingDevice.setStatus(deviceDetails.isStatus());
        return deviceRepository.save(existingDevice);
    }

    // Bir cihazı, sadece o anki kullanıcıya aitse siler.
    public void deleteDevice(Long deviceId) {
        // getDeviceById metodu, silinecek cihazın bu kullanıcıya ait olup olmadığını kontrol eder.
        Device deviceToDelete = getDeviceById(deviceId);
        deviceRepository.delete(deviceToDelete);
    }

    // Bir odadaki cihazları, sadece o oda o anki kullanıcıya aitse getirir.
    public List<Device> getDevicesByRoomId(Long roomId) {
        User currentUser = getCurrentUser();
        // Önce odanın var olup olmadığını VE bu kullanıcıya ait olup olmadığını kontrol edelim.
        roomRepository.findByIdAndUser(roomId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        // Oda bu kullanıcıya aitse, o odaya ait cihazları döndür.
        return deviceRepository.findByRoomId(roomId);
    }

    // NOT: getAllDevices, getDevicesByStatus, searchDevicesByName gibi genel listeleme metodları
    // bu aşamada mimari olarak artık mantıklı değildir. Çünkü "tüm sahipsiz cihazlar" diye bir konsept yoktur.
    // Cihazlar her zaman bir oda veya kullanıcı bağlamında listelenmelidir. Bu yüzden bu metodları şimdilik
    // bu servisten kaldırıyoruz. Gerekirse, "kullanıcıya ait tüm cihazları" listeleyen yeni bir metod eklenebilir.
}
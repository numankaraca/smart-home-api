package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.repository.RoomRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Sadece o anki kullanıcının odalarını getirir.
    public List<Room> getAllRooms() {
        User currentUser = getCurrentUser();
        return roomRepository.findByUser(currentUser);
    }

    // Sadece o anki kullanıcıya ait olan bir odayı ID ile getirir.
    public Room getRoomById(Long id) {
        User currentUser = getCurrentUser();
        return roomRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    // Yeni odayı o anki kullanıcıya bağlayarak ekler.
    public Room addRoom(Room room) {
        User currentUser = getCurrentUser();
        room.setUser(currentUser);
        return roomRepository.save(room);
    }

    // --- GÜNCELLENDİ: GÜVENLİ GÜNCELLEME ---
    // Sadece o anki kullanıcıya ait olan bir odayı günceller.
    public Room updateRoom(Long id, Room roomDetails) {
        // getRoomById metodu, odayı hem ID'ye hem de kullanıcıya göre bulur.
        // Eğer oda başkasına aitse, burada hata fırlatır.
        Room existingRoom = getRoomById(id);

        existingRoom.setName(roomDetails.getName());
        return roomRepository.save(existingRoom);
    }

    // --- GÜNCELLENDİ: GÜVENLİ SİLME ---
    // Sadece o anki kullanıcıya ait olan bir odayı siler.
    public void deleteRoom(Long id) {
        // getRoomById metodu, silinecek odanın gerçekten bu kullanıcıya ait olup olmadığını kontrol eder.
        Room roomToDelete = getRoomById(id);
        roomRepository.delete(roomToDelete);
    }

    // Spring Security'nin kasasından o anki kullanıcıyı getiren yardımcı metod.
    private User getCurrentUser() {
        // Bu metodun çalışabilmesi için User sınıfımızın UserDetails'i implemente etmesi gerekir (ki zaten öyle).
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
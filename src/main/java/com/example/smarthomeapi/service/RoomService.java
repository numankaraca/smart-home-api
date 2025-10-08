package com.example.smarthomeapi.service;

import com.example.smarthomeapi.exception.ResourceNotFoundException;
import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room existingRoom = getRoomById(id); // Varlığını kontrol et, yoksa hata fırlatır
        existingRoom.setName(roomDetails.getName());
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        Room roomToDelete = getRoomById(id); // Varlığını kontrol et
        roomRepository.delete(roomToDelete);
    }
}
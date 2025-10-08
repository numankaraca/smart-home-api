package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.model.Device; // YENİ IMPORT
import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.service.DeviceService; // YENİ IMPORT
import com.example.smarthomeapi.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;
    private final DeviceService deviceService; // YENİ ALAN

    // Constructor'ı DeviceService'i de alacak şekilde güncelliyoruz.
    public RoomController(RoomService roomService, DeviceService deviceService) {
        this.roomService = roomService;
        this.deviceService = deviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@Valid @RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @Valid @RequestBody Room roomDetails) {
        return roomService.updateRoom(id, roomDetails);
    }

    @Operation(summary = "Delete a room by its ID")
    @ApiResponse(responseCode = "204", description = "Room deleted successfully")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // BU SATIRI EKLE
    public void deleteRoom(@PathVariable Long id) { // DÖNÜŞ TİPİNİ void YAP
        roomService.deleteRoom(id);
    }

    // --- YENİ EKLENEN METOD ---
    @PostMapping("/{roomId}/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public Device addDeviceToRoom(@PathVariable Long roomId, @Valid @RequestBody Device newDevice) {
        return deviceService.addDeviceToRoom(roomId, newDevice);
    }
    // ---------------------------

    @GetMapping("/{roomId}/devices")
    public List<Device> getDevicesInRoom(@PathVariable Long roomId) {
        return deviceService.getDevicesByRoomId(roomId);
    }
}
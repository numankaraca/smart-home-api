package com.example.smarthomeapi.repository;

import com.example.smarthomeapi.model.Device;
import com.example.smarthomeapi.model.User; // YENİ IMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // YENİ IMPORT

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByStatus(boolean status);

    List<Device> findByNameContainingIgnoreCase(String keyword);

    List<Device> findByRoomId(Long roomId);

    // --- YENİ EKLENEN METOD ---
    // Bir cihazı, kendi ID'sine VE ait olduğu odanın sahibine (user) göre bulan metod.
    Optional<Device> findByIdAndRoomUser(Long deviceId, User user);
}
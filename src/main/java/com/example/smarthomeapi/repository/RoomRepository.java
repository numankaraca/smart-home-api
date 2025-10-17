package com.example.smarthomeapi.repository;

import com.example.smarthomeapi.model.Room;
import com.example.smarthomeapi.model.User; // YENİ IMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // YENİ IMPORT
import java.util.Optional; // YENİ IMPORT

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // --- YENİ EKLENEN METODLAR ---

    // Belirli bir kullanıcıya ait tüm odaları bulan metod.
    List<Room> findByUser(User user);

    // Belirli bir kullanıcıya ait olan ve ID'si eşleşen tek bir odayı bulan metod.
    Optional<Room> findByIdAndUser(Long id, User user);
}
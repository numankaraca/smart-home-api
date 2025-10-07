package com.example.smarthomeapi.repository;

import com.example.smarthomeapi.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // YENİ IMPORT

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    // YENİ EKLENEN METOD
    List<Device> findByStatus(boolean status);
    List<Device> findByNameContainingIgnoreCase(String keyword);
}
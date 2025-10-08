package com.example.smarthomeapi.model;

import jakarta.persistence.*; // YENİ IMPORT
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List; // YENİ IMPORT

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room name cannot be blank")
    private String name;

    // --- YENİ EKLENEN İLİŞKİ ALANI ---
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;
    // ------------------------------------
}
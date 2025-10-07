package com.example.smarthomeapi.model;

// YENİ EKLENEN IMPORT'LAR (Gerekli kütüphaneleri dahil ediyoruz)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // <-- BU ÇOK ÖNEMLİ: Bu sınıfın bir veritabanı varlığı (Entity) olduğunu belirtir.
@Table(name = "devices") // <-- Veritabanındaki tablo adının "devices" olacağını belirtir. Bu iyi bir pratiktir.
public class Device {

    @Id // <-- Bu alanın tablonun Primary Key'i (Birincil Anahtar) olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- Bu ID'nin veritabanı tarafından otomatik olarak (1, 2, 3 şeklinde artarak) oluşturulacağını söyler.
    private Long id;

    private String name;
    private boolean status;
}
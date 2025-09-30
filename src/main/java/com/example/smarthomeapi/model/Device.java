package com.example.smarthomeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private Long id;
    private String name;
    private boolean status; // true = açık, false = kapalı
}
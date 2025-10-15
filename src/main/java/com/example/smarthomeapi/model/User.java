package com.example.smarthomeapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") // "user" SQL'de özel bir kelime olabildiği için "users" kullanmak daha güvenlidir.
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Boş olamaz ve her kullanıcı adı benzersiz olmalı
    private String username;

    @Column(nullable = false)
    private String password;

    // Gelecekte "ADMIN", "USER" gibi roller için bu alanı kullanacağız.
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String plainTextPassword) {
        // 1. Bu kullanıcı adıyla başka bir kullanıcı var mı diye kontrol et.
        if (userRepository.findByUsername(username).isPresent()) {
            // Eğer varsa, hata fırlat (daha sonra bunu kendi özel hatamızla değiştireceğiz).
            throw new IllegalStateException("Username already exists: " + username);
        }

        // 2. Gelen şifreyi BCrypt ile şifrele.
        String encodedPassword = passwordEncoder.encode(plainTextPassword);

        // 3. Yeni kullanıcı nesnesini oluştur.
        User newUser = new User(username, encodedPassword, "ROLE_USER"); // Varsayılan rol "USER" olsun.

        // 4. Şifrelenmiş şifreyle yeni kullanıcıyı veritabanına kaydet.
        return userRepository.save(newUser);
    }
}
package com.example.smarthomeapi.service;

import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.repository.UserRepository;
import org.springframework.context.annotation.Lazy; // YENİ IMPORT
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // --- DEĞİŞİKLİK SADECE BURADA ---
    // Spring'e, PasswordEncoder'ı hemen değil, sadece ihtiyaç duyulduğunda oluşturmasını söylüyoruz.
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String plainTextPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists: " + username);
        }
        String encodedPassword = passwordEncoder.encode(plainTextPassword);
        User newUser = new User(username, encodedPassword, "ROLE_USER");
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
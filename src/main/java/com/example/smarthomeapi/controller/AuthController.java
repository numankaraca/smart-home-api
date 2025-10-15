package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.dto.JwtResponse;
import com.example.smarthomeapi.dto.LoginRequest;
import com.example.smarthomeapi.dto.RegisterRequest;
import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.service.JwtService;
import com.example.smarthomeapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(
                registerRequest.username(),
                registerRequest.password()
        );
    }

    // --- YENİ EKLENEN METOD: KULLANICI GİRİŞİ ---
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. Spring Security'ye kimlik doğrulama isteği gönder.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        // 2. Eğer kimlik doğrulama başarılıysa (hata fırlatmadıysa), kullanıcı detaylarını al.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Kullanıcı için bir JWT oluştur.
        String token = jwtService.generateToken(userDetails);

        // 4. JWT'yi kullanıcıya geri döndür.
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
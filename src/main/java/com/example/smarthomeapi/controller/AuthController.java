package com.example.smarthomeapi.controller;

import com.example.smarthomeapi.dto.RegisterRequest;
import com.example.smarthomeapi.model.User;
import com.example.smarthomeapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(
                registerRequest.username(),
                registerRequest.password()
        );
    }
}
package com.serhiikovtyka.webchat.auth.controller;

import com.serhiikovtyka.webchat.auth.dto.LoginRequest;
import com.serhiikovtyka.webchat.auth.dto.LoginResponse;
import com.serhiikovtyka.webchat.auth.dto.RegisterRequest;
import com.serhiikovtyka.webchat.auth.model.User;
import com.serhiikovtyka.webchat.auth.util.JwtTokenUtil;
import com.serhiikovtyka.webchat.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username is already taken"));
        }
        userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
      return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (authenticatedUser != null) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtTokenUtil.generateAccessToken(authenticatedUser.getUsername()));


            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password"));
    }

}

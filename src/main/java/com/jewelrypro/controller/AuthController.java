package com.jewelrypro.controller;

import com.jewelrypro.dto.auth.LoginRequest;
import com.jewelrypro.dto.auth.LoginResponse;
import com.jewelrypro.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request for email: {}", request.getEmail());
        
        try {
            LoginResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed for email: {}", request.getEmail(), e);
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // In stateless JWT, logout is handled client-side by removing the token
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser() {
        // This will be implemented based on security context
        return ResponseEntity.ok("Current user endpoint - implementation needed");
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken() {
        // JWT refresh token implementation
        return ResponseEntity.ok("Token refresh endpoint - implementation needed");
    }
}
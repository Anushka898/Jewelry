package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SettingsController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSettings() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Settings module - Coming Soon");
        response.put("module", "settings");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> updateSettings(@RequestBody Object settings) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Update settings - Coming Soon");
        response.put("module", "settings");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/business")
    public ResponseEntity<Map<String, Object>> getBusinessSettings() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Business settings - Coming Soon");
        response.put("settingsType", "business");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemSettings() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "System settings - Coming Soon");
        response.put("settingsType", "system");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUserSettings() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User settings - Coming Soon");
        response.put("settingsType", "users");
        return ResponseEntity.ok(response);
    }
}
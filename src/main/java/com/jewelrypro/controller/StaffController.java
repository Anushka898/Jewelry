package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StaffController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStaff() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Staff management module - Coming Soon");
        response.put("module", "staff");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/commissions")
    public ResponseEntity<Map<String, Object>> getCommissions() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Commission tracking - Coming Soon");
        response.put("module", "staff");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/performance")
    public ResponseEntity<Map<String, Object>> getStaffPerformance(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Staff performance metrics - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }
}
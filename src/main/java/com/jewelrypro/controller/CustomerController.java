package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCustomers() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Customer management module - Coming Soon");
        response.put("module", "customers");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addCustomer(@RequestBody Object customer) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Add customer - Coming Soon");
        response.put("module", "customers");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCustomer(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Get customer details - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<Map<String, Object>> getCustomerHistory(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Get customer purchase history - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }
}
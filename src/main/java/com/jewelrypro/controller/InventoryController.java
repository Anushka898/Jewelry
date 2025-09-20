package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getInventoryItems() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Inventory module - Coming Soon");
        response.put("module", "inventory");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addInventoryItem(@RequestBody Object item) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Add inventory item - Coming Soon");
        response.put("module", "inventory");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getInventoryItem(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Get inventory item - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateInventoryItem(@PathVariable Long id, @RequestBody Object item) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Update inventory item - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteInventoryItem(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Delete inventory item - Coming Soon");
        response.put("id", id);
        return ResponseEntity.ok(response);
    }
}
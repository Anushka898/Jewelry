package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPurchases() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Purchase management module - Coming Soon");
        response.put("module", "purchases");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addPurchase(@RequestBody Object purchase) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Add new purchase - Coming Soon");
        response.put("module", "purchases");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ocr/upload")
    public ResponseEntity<Map<String, Object>> uploadInvoiceForOCR(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OCR invoice processing - Coming Soon");
        response.put("fileName", file.getOriginalFilename());
        response.put("size", file.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<Map<String, Object>> getSuppliers() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Supplier management - Coming Soon");
        response.put("module", "purchases");
        return ResponseEntity.ok(response);
    }
}
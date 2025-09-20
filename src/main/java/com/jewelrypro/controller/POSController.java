package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class POSController {

    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSales() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "POS Sales module - Coming Soon");
        response.put("module", "pos");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sale")
    public ResponseEntity<Map<String, Object>> createSale(@RequestBody Object sale) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Create new sale - Coming Soon");
        response.put("module", "pos");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoices")
    public ResponseEntity<Map<String, Object>> getInvoices() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Get invoices - Coming Soon");
        response.put("module", "pos");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/invoice/{saleId}")
    public ResponseEntity<Map<String, Object>> generateInvoice(@PathVariable Long saleId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Generate invoice - Coming Soon");
        response.put("saleId", saleId);
        return ResponseEntity.ok(response);
    }
}
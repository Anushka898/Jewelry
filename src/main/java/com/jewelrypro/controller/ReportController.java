package com.jewelrypro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getReports() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reports module - Coming Soon");
        response.put("module", "reports");
        response.put("status", "placeholder");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSalesReport() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sales report - Coming Soon");
        response.put("reportType", "sales");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Object>> getInventoryReport() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Inventory report - Coming Soon");
        response.put("reportType", "inventory");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gst")
    public ResponseEntity<Map<String, Object>> getGSTReport() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "GST report - Coming Soon");
        response.put("reportType", "gst");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/export")
    public ResponseEntity<Map<String, Object>> exportReport(@RequestBody Object exportRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Export report - Coming Soon");
        response.put("module", "reports");
        return ResponseEntity.ok(response);
    }
}
package com.jewelrypro.controller;

import com.jewelrypro.dto.DashboardResponseDTO;
import com.jewelrypro.dto.MetalPricesDTO;
import com.jewelrypro.dto.RecentActivityDTO;
import com.jewelrypro.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboardData() {
        DashboardResponseDTO dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardResponseDTO.DashboardStats> getDashboardStats() {
        DashboardResponseDTO.DashboardStats stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/metal-prices")
    public ResponseEntity<MetalPricesDTO> getMetalPrices() {
        MetalPricesDTO metalPrices = dashboardService.getMetalPrices();
        return ResponseEntity.ok(metalPrices);
    }

    @PostMapping("/metal-prices/refresh")
    public ResponseEntity<MetalPricesDTO> refreshMetalPrices() {
        MetalPricesDTO metalPrices = dashboardService.refreshMetalPrices();
        return ResponseEntity.ok(metalPrices);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<RecentActivityDTO>> getRecentActivities() {
        List<RecentActivityDTO> activities = dashboardService.getRecentActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("JewelryPro Backend is running!");
    }
}
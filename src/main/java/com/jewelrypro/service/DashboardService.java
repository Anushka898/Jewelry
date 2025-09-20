package com.jewelrypro.service;

import com.jewelrypro.dto.DashboardResponseDTO;
import com.jewelrypro.dto.MetalPricesDTO;
import com.jewelrypro.dto.RecentActivityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    @Cacheable(value = "dashboardData", unless = "#result == null")
    public DashboardResponseDTO getDashboardData() {
        log.info("Fetching complete dashboard data");
        
        DashboardResponseDTO response = new DashboardResponseDTO();
        response.setStats(getDashboardStats());
        response.setMetalPrices(getMetalPrices());
        response.setRecentActivities(getRecentActivities());
        
        return response;
    }

    public DashboardResponseDTO.DashboardStats getDashboardStats() {
        log.info("Fetching dashboard statistics");
        
        // Mock data - In real implementation, this would come from database
        DashboardResponseDTO.DashboardStats stats = new DashboardResponseDTO.DashboardStats();
        stats.setTotalSales(new BigDecimal("125000"));
        stats.setSalesChange(12.5);
        stats.setLowStockAlerts(15);
        stats.setStaffProgress(78);
        stats.setTotalCustomers(1243);
        stats.setNewCustomers(23);
        
        return stats;
    }

    @Cacheable(value = "metalPrices", unless = "#result == null")
    public MetalPricesDTO getMetalPrices() {
        log.info("Fetching metal prices");
        
        MetalPricesDTO metalPrices = new MetalPricesDTO();
        metalPrices.setLastUpdated(LocalDateTime.now());
        
        // Gold prices
        Map<String, MetalPricesDTO.MetalPriceData> gold = new HashMap<>();
        gold.put("18k", new MetalPricesDTO.MetalPriceData(new BigDecimal("4596"), 1.2, "/g"));
        gold.put("22k", new MetalPricesDTO.MetalPriceData(new BigDecimal("5616"), 1.5, "/g"));
        gold.put("24k", new MetalPricesDTO.MetalPriceData(new BigDecimal("6245"), 2.1, "/g"));
        metalPrices.setGold(gold);
        
        // Silver prices
        Map<String, MetalPricesDTO.MetalPriceData> silver = new HashMap<>();
        silver.put("92.5%", new MetalPricesDTO.MetalPriceData(new BigDecimal("75"), -0.5, "/g"));
        silver.put("99.9%", new MetalPricesDTO.MetalPriceData(new BigDecimal("82"), 0.3, "/g"));
        metalPrices.setSilver(silver);
        
        // Platinum prices
        Map<String, MetalPricesDTO.MetalPriceData> platinum = new HashMap<>();
        platinum.put("95%", new MetalPricesDTO.MetalPriceData(new BigDecimal("2850"), 0.7, "/g"));
        metalPrices.setPlatinum(platinum);
        
        // Diamond prices
        Map<String, MetalPricesDTO.MetalPriceData> diamond = new HashMap<>();
        diamond.put("VVS1", new MetalPricesDTO.MetalPriceData(new BigDecimal("45000"), 3.2, "/ct"));
        diamond.put("VS1", new MetalPricesDTO.MetalPriceData(new BigDecimal("35000"), 2.8, "/ct"));
        metalPrices.setDiamond(diamond);
        
        return metalPrices;
    }

    public MetalPricesDTO refreshMetalPrices() {
        log.info("Refreshing metal prices from external API");
        
        // In real implementation, this would call external metal price API
        // For now, we'll return updated mock data with slight variations
        MetalPricesDTO metalPrices = getMetalPrices();
        
        // Simulate price updates
        metalPrices.setLastUpdated(LocalDateTime.now());
        
        // Add small random variations to simulate real price changes
        Random random = new Random();
        metalPrices.getGold().forEach((key, value) -> {
            double changeVariation = (random.nextDouble() - 0.5) * 0.5; // ±0.25% variation
            value.setChange(value.getChange() + changeVariation);
        });
        
        return metalPrices;
    }

    public List<RecentActivityDTO> getRecentActivities() {
        log.info("Fetching recent activities");
        
        List<RecentActivityDTO> activities = new ArrayList<>();
        
        LocalDateTime now = LocalDateTime.now();
        
        activities.add(new RecentActivityDTO(
            1L, "sale", "New sale - Invoice #INV-2024-001", 
            new BigDecimal("15000"), now.minus(2, ChronoUnit.MINUTES), "2 minutes ago"
        ));
        
        activities.add(new RecentActivityDTO(
            2L, "alert", "Low stock alert - Gold Ring 18k", 
            null, now.minus(15, ChronoUnit.MINUTES), "15 minutes ago"
        ));
        
        activities.add(new RecentActivityDTO(
            3L, "customer", "New customer registered - Sarah Johnson", 
            null, now.minus(1, ChronoUnit.HOURS), "1 hour ago"
        ));
        
        activities.add(new RecentActivityDTO(
            4L, "payment", "Payment received", 
            new BigDecimal("25000"), now.minus(2, ChronoUnit.HOURS), "2 hours ago"
        ));
        
        return activities;
    }

    private String formatTimeAgo(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(timestamp, now);
        
        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else {
            long hours = ChronoUnit.HOURS.between(timestamp, now);
            if (hours < 24) {
                return hours + (hours == 1 ? " hour ago" : " hours ago");
            } else {
                long days = ChronoUnit.DAYS.between(timestamp, now);
                return days + (days == 1 ? " day ago" : " days ago");
            }
        }
    }
}
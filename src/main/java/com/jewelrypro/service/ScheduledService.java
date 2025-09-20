package com.jewelrypro.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final DashboardService dashboardService;
    private final CacheManager cacheManager;

    /**
     * Update metal prices every 5 minutes (300,000 milliseconds)
     */
    @Scheduled(fixedRate = 300000)
    public void updateMetalPrices() {
        log.info("Scheduled metal price update started");
        
        try {
            // Clear cache for metal prices
            if (cacheManager.getCache("metalPrices") != null) {
                cacheManager.getCache("metalPrices").clear();
            }
            
            // Refresh metal prices
            dashboardService.refreshMetalPrices();
            
            log.info("Metal prices updated successfully");
        } catch (Exception e) {
            log.error("Error updating metal prices: {}", e.getMessage(), e);
        }
    }

    /**
     * Update dashboard data every 2 minutes (120,000 milliseconds)
     */
    @Scheduled(fixedRate = 120000)
    public void updateDashboardData() {
        log.info("Scheduled dashboard data update started");
        
        try {
            // Clear dashboard cache
            if (cacheManager.getCache("dashboardData") != null) {
                cacheManager.getCache("dashboardData").clear();
            }
            
            // This will trigger fresh data fetch on next request
            log.info("Dashboard data cache cleared successfully");
        } catch (Exception e) {
            log.error("Error updating dashboard data: {}", e.getMessage(), e);
        }
    }
}
package com.jewelrypro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {
    private DashboardStats stats;
    private MetalPricesDTO metalPrices;
    private List<RecentActivityDTO> recentActivities;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardStats {
        private BigDecimal totalSales;
        private Double salesChange;
        private Integer lowStockAlerts;
        private Integer staffProgress;
        private Integer totalCustomers;
        private Integer newCustomers;
    }
}
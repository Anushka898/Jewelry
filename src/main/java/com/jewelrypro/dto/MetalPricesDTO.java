package com.jewelrypro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetalPricesDTO {
    private LocalDateTime lastUpdated;
    private Map<String, MetalPriceData> gold;
    private Map<String, MetalPriceData> silver;
    private Map<String, MetalPriceData> platinum;
    private Map<String, MetalPriceData> diamond;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetalPriceData {
        private BigDecimal price;
        private Double change;
        private String unit; // /g, /ct, /tola
    }
}
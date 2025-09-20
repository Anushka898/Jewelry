package com.jewelrypro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityDTO {
    private Long id;
    private String type; // sale, alert, customer, payment
    private String description;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String timeAgo;
}
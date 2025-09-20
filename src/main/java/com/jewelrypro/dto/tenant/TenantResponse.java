package com.jewelrypro.dto.tenant;

import com.jewelrypro.entity.Tenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    
    private Long id;
    private String tenantId;
    private String companyName;
    private String businessType;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    
    private Tenant.TenantStatus status;
    private Tenant.SubscriptionPlan subscriptionPlan;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    
    private String currency;
    private String timezone;
    private String language;
    
    // Statistics
    private long totalUsers;
    private long activeUsers;
    
    // Admin user info
    private String adminName;
    private String adminEmail;
}
package com.jewelrypro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String tenantId; // Unique identifier for tenant
    
    @Column(nullable = false)
    private String companyName;
    
    @Column(nullable = false)
    private String businessType;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    
    @Enumerated(EnumType.STRING)
    private TenantStatus status = TenantStatus.ACTIVE;
    
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan = SubscriptionPlan.BASIC;
    
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private String createdBy; // Super admin who created this tenant
    
    // Business specific settings
    private String currency = "INR";
    private String timezone = "Asia/Kolkata";
    private String language = "en";
    
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    
    public enum TenantStatus {
        ACTIVE, SUSPENDED, INACTIVE, TRIAL
    }
    
    public enum SubscriptionPlan {
        BASIC, PREMIUM, ENTERPRISE
    }
}
package com.jewelrypro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private PermissionCategory category;
    
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;
    
    public enum PermissionCategory {
        DASHBOARD, INVENTORY, POS, CUSTOMERS, STAFF, PURCHASES, REPORTS, SETTINGS, 
        USER_MANAGEMENT, TENANT_MANAGEMENT, SUPER_ADMIN
    }
}
package com.jewelrypro.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn;
    
    // User information
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String initials;
    
    // Tenant information
    private String tenantId;
    private String companyName;
    
    // Roles and permissions
    private boolean isSuperAdmin;
    private String[] roles;
    private String[] permissions;
    
    public LoginResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
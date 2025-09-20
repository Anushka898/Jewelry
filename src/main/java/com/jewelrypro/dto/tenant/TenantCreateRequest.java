package com.jewelrypro.dto.tenant;

import com.jewelrypro.entity.Tenant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantCreateRequest {
    
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;
    
    @NotBlank(message = "Business type is required")
    private String businessType;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    
    private Tenant.SubscriptionPlan subscriptionPlan = Tenant.SubscriptionPlan.BASIC;
    
    // Admin user details for the tenant
    @NotBlank(message = "Admin first name is required")
    private String adminFirstName;
    
    @NotBlank(message = "Admin last name is required")
    private String adminLastName;
    
    @NotBlank(message = "Admin email is required")
    @Email(message = "Admin email should be valid")
    private String adminEmail;
    
    @NotBlank(message = "Admin password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String adminPassword;
    
    private String adminPhone;
    
    // Business settings
    private String currency = "INR";
    private String timezone = "Asia/Kolkata";
    private String language = "en";
}
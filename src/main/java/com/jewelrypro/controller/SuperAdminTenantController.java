package com.jewelrypro.controller;

import com.jewelrypro.dto.tenant.TenantCreateRequest;
import com.jewelrypro.dto.tenant.TenantResponse;
import com.jewelrypro.entity.Tenant;
import com.jewelrypro.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/super-admin/tenants")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@Slf4j
public class SuperAdminTenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(
            @Valid @RequestBody TenantCreateRequest request,
            Principal principal) {
        log.info("Creating new tenant: {} by super admin: {}", 
                 request.getCompanyName(), principal.getName());
        
        try {
            TenantResponse response = tenantService.createTenant(request, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Failed to create tenant: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create tenant: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        log.info("Fetching all tenants");
        
        List<TenantResponse> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TenantResponse>> getActiveTenants() {
        log.info("Fetching active tenants");
        
        List<TenantResponse> tenants = tenantService.getActiveTenants();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id) {
        log.info("Fetching tenant by id: {}", id);
        
        try {
            TenantResponse tenant = tenantService.getTenantById(id);
            return ResponseEntity.ok(tenant);
        } catch (Exception e) {
            log.error("Tenant not found: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-tenant-id/{tenantId}")
    public ResponseEntity<TenantResponse> getTenantByTenantId(@PathVariable String tenantId) {
        log.info("Fetching tenant by tenantId: {}", tenantId);
        
        try {
            TenantResponse tenant = tenantService.getTenantByTenantId(tenantId);
            return ResponseEntity.ok(tenant);
        } catch (Exception e) {
            log.error("Tenant not found: {}", tenantId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{tenantId}/status")
    public ResponseEntity<TenantResponse> updateTenantStatus(
            @PathVariable String tenantId, 
            @RequestParam Tenant.TenantStatus status) {
        log.info("Updating tenant {} status to {}", tenantId, status);
        
        try {
            TenantResponse tenant = tenantService.updateTenantStatus(tenantId, status);
            return ResponseEntity.ok(tenant);
        } catch (Exception e) {
            log.error("Failed to update tenant status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update tenant status: " + e.getMessage());
        }
    }
}
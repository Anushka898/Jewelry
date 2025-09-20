package com.jewelrypro.service;

import com.jewelrypro.dto.tenant.TenantCreateRequest;
import com.jewelrypro.dto.tenant.TenantResponse;
import com.jewelrypro.entity.Role;
import com.jewelrypro.entity.Tenant;
import com.jewelrypro.entity.User;
import com.jewelrypro.repository.RoleRepository;
import com.jewelrypro.repository.TenantRepository;
import com.jewelrypro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TenantResponse createTenant(TenantCreateRequest request, String createdBy) {
        log.info("Creating new tenant: {} by {}", request.getCompanyName(), createdBy);

        // Check if tenant email already exists
        if (tenantRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Tenant with this email already exists");
        }

        // Check if admin email already exists
        if (userRepository.existsByEmailAndTenantIsNull(request.getAdminEmail()) ||
            userRepository.findByEmail(request.getAdminEmail()).isPresent()) {
            throw new RuntimeException("Admin email already exists in the system");
        }

        // Generate unique tenant ID
        String tenantId = generateTenantId();
        while (tenantRepository.existsByTenantId(tenantId)) {
            tenantId = generateTenantId();
        }

        // Create tenant
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId);
        tenant.setCompanyName(request.getCompanyName());
        tenant.setBusinessType(request.getBusinessType());
        tenant.setEmail(request.getEmail());
        tenant.setPhone(request.getPhone());
        tenant.setAddress(request.getAddress());
        tenant.setCity(request.getCity());
        tenant.setState(request.getState());
        tenant.setCountry(request.getCountry());
        tenant.setPincode(request.getPincode());
        tenant.setStatus(Tenant.TenantStatus.ACTIVE);
        tenant.setSubscriptionPlan(request.getSubscriptionPlan());
        tenant.setSubscriptionStartDate(LocalDateTime.now());
        tenant.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1)); // 1 month trial
        tenant.setCreatedBy(createdBy);
        tenant.setCurrency(request.getCurrency());
        tenant.setTimezone(request.getTimezone());
        tenant.setLanguage(request.getLanguage());

        tenant = tenantRepository.save(tenant);

        // Create admin user for the tenant
        User adminUser = new User();
        adminUser.setFirstName(request.getAdminFirstName());
        adminUser.setLastName(request.getAdminLastName());
        adminUser.setEmail(request.getAdminEmail());
        adminUser.setPassword(passwordEncoder.encode(request.getAdminPassword()));
        adminUser.setPhone(request.getAdminPhone());
        adminUser.setStatus(User.UserStatus.ACTIVE);
        adminUser.setTenant(tenant);
        adminUser.setIsSuperAdmin(false);

        // Assign TENANT_ADMIN role
        Role adminRole = roleRepository.findByName("TENANT_ADMIN")
            .orElseThrow(() -> new RuntimeException("TENANT_ADMIN role not found"));
        adminUser.setRoles(List.of(adminRole));

        userRepository.save(adminUser);

        log.info("Created tenant: {} with admin user: {}", tenantId, adminUser.getEmail());

        return mapToTenantResponse(tenant, 1, 1, adminUser.getFullName(), adminUser.getEmail());
    }

    public List<TenantResponse> getAllTenants() {
        log.info("Fetching all tenants");
        
        List<Tenant> tenants = tenantRepository.findAll();
        
        return tenants.stream()
            .map(this::mapToTenantResponseWithStats)
            .collect(Collectors.toList());
    }

    public TenantResponse getTenantById(Long id) {
        log.info("Fetching tenant by id: {}", id);
        
        Tenant tenant = tenantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
            
        return mapToTenantResponseWithStats(tenant);
    }

    public TenantResponse getTenantByTenantId(String tenantId) {
        log.info("Fetching tenant by tenantId: {}", tenantId);
        
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
            
        return mapToTenantResponseWithStats(tenant);
    }

    public List<TenantResponse> getActiveTenants() {
        log.info("Fetching active tenants");
        
        List<Tenant> tenants = tenantRepository.findActiveTenants();
        
        return tenants.stream()
            .map(this::mapToTenantResponseWithStats)
            .collect(Collectors.toList());
    }

    @Transactional
    public TenantResponse updateTenantStatus(String tenantId, Tenant.TenantStatus status) {
        log.info("Updating tenant {} status to {}", tenantId, status);
        
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
            
        tenant.setStatus(status);
        tenant = tenantRepository.save(tenant);
        
        return mapToTenantResponseWithStats(tenant);
    }

    private String generateTenantId() {
        return "TNT_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TenantResponse mapToTenantResponse(Tenant tenant, long totalUsers, long activeUsers, 
                                            String adminName, String adminEmail) {
        TenantResponse response = new TenantResponse();
        response.setId(tenant.getId());
        response.setTenantId(tenant.getTenantId());
        response.setCompanyName(tenant.getCompanyName());
        response.setBusinessType(tenant.getBusinessType());
        response.setEmail(tenant.getEmail());
        response.setPhone(tenant.getPhone());
        response.setAddress(tenant.getAddress());
        response.setCity(tenant.getCity());
        response.setState(tenant.getState());
        response.setCountry(tenant.getCountry());
        response.setPincode(tenant.getPincode());
        response.setStatus(tenant.getStatus());
        response.setSubscriptionPlan(tenant.getSubscriptionPlan());
        response.setSubscriptionStartDate(tenant.getSubscriptionStartDate());
        response.setSubscriptionEndDate(tenant.getSubscriptionEndDate());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setUpdatedAt(tenant.getUpdatedAt());
        response.setCreatedBy(tenant.getCreatedBy());
        response.setCurrency(tenant.getCurrency());
        response.setTimezone(tenant.getTimezone());
        response.setLanguage(tenant.getLanguage());
        response.setTotalUsers(totalUsers);
        response.setActiveUsers(activeUsers);
        response.setAdminName(adminName);
        response.setAdminEmail(adminEmail);
        
        return response;
    }

    private TenantResponse mapToTenantResponseWithStats(Tenant tenant) {
        long totalUsers = userRepository.countUsersByTenant(tenant.getTenantId());
        long activeUsers = userRepository.findActiveUsersByTenant(tenant.getTenantId()).size();
        
        // Get admin user info
        List<User> users = userRepository.findByTenant(tenant);
        User adminUser = users.stream()
            .filter(user -> user.getRoles().stream()
                .anyMatch(role -> "TENANT_ADMIN".equals(role.getName())))
            .findFirst()
            .orElse(users.isEmpty() ? null : users.get(0));
        
        String adminName = adminUser != null ? adminUser.getFullName() : "N/A";
        String adminEmail = adminUser != null ? adminUser.getEmail() : "N/A";
        
        return mapToTenantResponse(tenant, totalUsers, activeUsers, adminName, adminEmail);
    }
}
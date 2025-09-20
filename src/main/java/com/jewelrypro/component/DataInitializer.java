package com.jewelrypro.component;

import com.jewelrypro.entity.Permission;
import com.jewelrypro.entity.Role;
import com.jewelrypro.entity.User;
import com.jewelrypro.repository.PermissionRepository;
import com.jewelrypro.repository.RoleRepository;
import com.jewelrypro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("=".repeat(50));
        log.info("JewelryPro SaaS Backend Application Started Successfully!");
        log.info("=".repeat(50));
        log.info("📊 Dashboard API: http://localhost:8080/api/dashboard");
        log.info("🔧 H2 Console: http://localhost:8080/h2-console");
        log.info("📚 API Docs: http://localhost:8080/swagger-ui.html");
        log.info("🌐 Frontend: http://localhost:3000");
        log.info("🔐 Auth API: http://localhost:8080/api/auth");
        log.info("👑 Super Admin API: http://localhost:8080/api/super-admin");
        log.info("=".repeat(50));
        
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        log.info("Initializing default RBAC data...");
        
        // Create permissions
        initializePermissions();
        
        // Create roles
        initializeRoles();
        
        // Create default super admin
        createDefaultSuperAdmin();
        
        log.info("Default data initialization completed");
    }

    private void initializePermissions() {
        log.info("Creating default permissions...");
        
        // Dashboard permissions
        createPermissionIfNotExists("DASHBOARD_VIEW", "View dashboard", Permission.PermissionCategory.DASHBOARD);
        createPermissionIfNotExists("DASHBOARD_MANAGE", "Manage dashboard settings", Permission.PermissionCategory.DASHBOARD);
        
        // Inventory permissions
        createPermissionIfNotExists("INVENTORY_VIEW", "View inventory", Permission.PermissionCategory.INVENTORY);
        createPermissionIfNotExists("INVENTORY_CREATE", "Create inventory items", Permission.PermissionCategory.INVENTORY);
        createPermissionIfNotExists("INVENTORY_UPDATE", "Update inventory items", Permission.PermissionCategory.INVENTORY);
        createPermissionIfNotExists("INVENTORY_DELETE", "Delete inventory items", Permission.PermissionCategory.INVENTORY);
        
        // POS permissions
        createPermissionIfNotExists("POS_VIEW", "View POS", Permission.PermissionCategory.POS);
        createPermissionIfNotExists("POS_CREATE_SALE", "Create sales", Permission.PermissionCategory.POS);
        createPermissionIfNotExists("POS_MANAGE_BILLING", "Manage billing", Permission.PermissionCategory.POS);
        
        // Customer permissions
        createPermissionIfNotExists("CUSTOMERS_VIEW", "View customers", Permission.PermissionCategory.CUSTOMERS);
        createPermissionIfNotExists("CUSTOMERS_CREATE", "Create customers", Permission.PermissionCategory.CUSTOMERS);
        createPermissionIfNotExists("CUSTOMERS_UPDATE", "Update customers", Permission.PermissionCategory.CUSTOMERS);
        createPermissionIfNotExists("CUSTOMERS_DELETE", "Delete customers", Permission.PermissionCategory.CUSTOMERS);
        
        // Staff permissions
        createPermissionIfNotExists("STAFF_VIEW", "View staff", Permission.PermissionCategory.STAFF);
        createPermissionIfNotExists("STAFF_CREATE", "Create staff", Permission.PermissionCategory.STAFF);
        createPermissionIfNotExists("STAFF_UPDATE", "Update staff", Permission.PermissionCategory.STAFF);
        createPermissionIfNotExists("STAFF_DELETE", "Delete staff", Permission.PermissionCategory.STAFF);
        createPermissionIfNotExists("COMMISSION_VIEW", "View commissions", Permission.PermissionCategory.STAFF);
        createPermissionIfNotExists("COMMISSION_MANAGE", "Manage commissions", Permission.PermissionCategory.STAFF);
        
        // Purchase permissions
        createPermissionIfNotExists("PURCHASES_VIEW", "View purchases", Permission.PermissionCategory.PURCHASES);
        createPermissionIfNotExists("PURCHASES_CREATE", "Create purchases", Permission.PermissionCategory.PURCHASES);
        createPermissionIfNotExists("PURCHASES_UPDATE", "Update purchases", Permission.PermissionCategory.PURCHASES);
        createPermissionIfNotExists("PURCHASES_DELETE", "Delete purchases", Permission.PermissionCategory.PURCHASES);
        createPermissionIfNotExists("OCR_PROCESS", "Process OCR invoices", Permission.PermissionCategory.PURCHASES);
        
        // Reports permissions
        createPermissionIfNotExists("REPORTS_VIEW", "View reports", Permission.PermissionCategory.REPORTS);
        createPermissionIfNotExists("REPORTS_EXPORT", "Export reports", Permission.PermissionCategory.REPORTS);
        createPermissionIfNotExists("GST_MANAGE", "Manage GST reports", Permission.PermissionCategory.REPORTS);
        
        // Settings permissions
        createPermissionIfNotExists("SETTINGS_VIEW", "View settings", Permission.PermissionCategory.SETTINGS);
        createPermissionIfNotExists("SETTINGS_UPDATE", "Update settings", Permission.PermissionCategory.SETTINGS);
        
        // User Management permissions
        createPermissionIfNotExists("USER_VIEW", "View users", Permission.PermissionCategory.USER_MANAGEMENT);
        createPermissionIfNotExists("USER_CREATE", "Create users", Permission.PermissionCategory.USER_MANAGEMENT);
        createPermissionIfNotExists("USER_UPDATE", "Update users", Permission.PermissionCategory.USER_MANAGEMENT);
        createPermissionIfNotExists("USER_DELETE", "Delete users", Permission.PermissionCategory.USER_MANAGEMENT);
        createPermissionIfNotExists("USER_MANAGE_ROLES", "Manage user roles", Permission.PermissionCategory.USER_MANAGEMENT);
        
        // Super Admin permissions
        createPermissionIfNotExists("TENANT_VIEW", "View tenants", Permission.PermissionCategory.TENANT_MANAGEMENT);
        createPermissionIfNotExists("TENANT_CREATE", "Create tenants", Permission.PermissionCategory.TENANT_MANAGEMENT);
        createPermissionIfNotExists("TENANT_UPDATE", "Update tenants", Permission.PermissionCategory.TENANT_MANAGEMENT);
        createPermissionIfNotExists("TENANT_DELETE", "Delete tenants", Permission.PermissionCategory.TENANT_MANAGEMENT);
        createPermissionIfNotExists("SUPER_ADMIN_ACCESS", "Super admin access", Permission.PermissionCategory.SUPER_ADMIN);
    }

    private void initializeRoles() {
        log.info("Creating default roles...");
        
        // Super Admin Role
        createSuperAdminRole();
        
        // Tenant Roles
        createTenantAdminRole();
        createManagerRole();
        createSalespersonRole();
        createViewerRole();
    }

    private void createSuperAdminRole() {
        if (!roleRepository.existsByName("SUPER_ADMIN")) {
            Role role = new Role();
            role.setName("SUPER_ADMIN");
            role.setDescription("Super Administrator with full system access");
            role.setRoleType(Role.RoleType.SUPER_ADMIN);
            
            // Get all super admin permissions
            List<Permission> permissions = permissionRepository.findAll();
            role.setPermissions(permissions);
            
            roleRepository.save(role);
            log.info("Created SUPER_ADMIN role with {} permissions", permissions.size());
        }
    }

    private void createTenantAdminRole() {
        if (!roleRepository.existsByName("TENANT_ADMIN")) {
            Role role = new Role();
            role.setName("TENANT_ADMIN");
            role.setDescription("Tenant Administrator with full tenant access");
            role.setRoleType(Role.RoleType.TENANT);
            
            List<Permission> permissions = permissionRepository.findAll().stream()
                .filter(p -> p.getCategory() != Permission.PermissionCategory.SUPER_ADMIN &&
                           p.getCategory() != Permission.PermissionCategory.TENANT_MANAGEMENT)
                .toList();
            role.setPermissions(permissions);
            
            roleRepository.save(role);
            log.info("Created TENANT_ADMIN role with {} permissions", permissions.size());
        }
    }

    private void createManagerRole() {
        if (!roleRepository.existsByName("MANAGER")) {
            Role role = new Role();
            role.setName("MANAGER");
            role.setDescription("Manager with most operational permissions");
            role.setRoleType(Role.RoleType.TENANT);
            
            List<String> managerPermissions = List.of(
                "DASHBOARD_VIEW", "INVENTORY_VIEW", "INVENTORY_CREATE", "INVENTORY_UPDATE",
                "POS_VIEW", "POS_CREATE_SALE", "POS_MANAGE_BILLING",
                "CUSTOMERS_VIEW", "CUSTOMERS_CREATE", "CUSTOMERS_UPDATE",
                "STAFF_VIEW", "COMMISSION_VIEW",
                "PURCHASES_VIEW", "PURCHASES_CREATE", "PURCHASES_UPDATE", "OCR_PROCESS",
                "REPORTS_VIEW", "REPORTS_EXPORT", "GST_MANAGE",
                "SETTINGS_VIEW", "USER_VIEW"
            );
            
            List<Permission> permissions = permissionRepository.findAll().stream()
                .filter(p -> managerPermissions.contains(p.getName()))
                .toList();
            role.setPermissions(permissions);
            
            roleRepository.save(role);
            log.info("Created MANAGER role with {} permissions", permissions.size());
        }
    }

    private void createSalespersonRole() {
        if (!roleRepository.existsByName("SALESPERSON")) {
            Role role = new Role();
            role.setName("SALESPERSON");
            role.setDescription("Salesperson with sales and customer permissions");
            role.setRoleType(Role.RoleType.TENANT);
            
            List<String> salesPermissions = List.of(
                "DASHBOARD_VIEW", "INVENTORY_VIEW",
                "POS_VIEW", "POS_CREATE_SALE",
                "CUSTOMERS_VIEW", "CUSTOMERS_CREATE", "CUSTOMERS_UPDATE"
            );
            
            List<Permission> permissions = permissionRepository.findAll().stream()
                .filter(p -> salesPermissions.contains(p.getName()))
                .toList();
            role.setPermissions(permissions);
            
            roleRepository.save(role);
            log.info("Created SALESPERSON role with {} permissions", permissions.size());
        }
    }

    private void createViewerRole() {
        if (!roleRepository.existsByName("VIEWER")) {
            Role role = new Role();
            role.setName("VIEWER");
            role.setDescription("Viewer with read-only permissions");
            role.setRoleType(Role.RoleType.TENANT);
            
            List<String> viewerPermissions = List.of(
                "DASHBOARD_VIEW", "INVENTORY_VIEW", "POS_VIEW",
                "CUSTOMERS_VIEW", "STAFF_VIEW", "PURCHASES_VIEW", "REPORTS_VIEW"
            );
            
            List<Permission> permissions = permissionRepository.findAll().stream()
                .filter(p -> viewerPermissions.contains(p.getName()))
                .toList();
            role.setPermissions(permissions);
            
            roleRepository.save(role);
            log.info("Created VIEWER role with {} permissions", permissions.size());
        }
    }

    private void createDefaultSuperAdmin() {
        String superAdminEmail = "super@jewelrypro.com";
        
        if (!userRepository.existsByEmailAndTenantIsNull(superAdminEmail)) {
            log.info("Creating default super admin user...");
            
            User superAdmin = new User();
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setEmail(superAdminEmail);
            superAdmin.setPassword(passwordEncoder.encode("SuperAdmin@123"));
            superAdmin.setStatus(User.UserStatus.ACTIVE);
            superAdmin.setIsSuperAdmin(true);
            superAdmin.setTenant(null); // Super admin has no tenant
            
            Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                .orElseThrow(() -> new RuntimeException("SUPER_ADMIN role not found"));
            superAdmin.setRoles(List.of(superAdminRole));
            
            userRepository.save(superAdmin);
            
            log.info("=".repeat(50));
            log.info("🔐 DEFAULT SUPER ADMIN CREATED");
            log.info("📧 Email: {}", superAdminEmail);
            log.info("🔑 Password: SuperAdmin@123");
            log.info("⚠️  PLEASE CHANGE THE DEFAULT PASSWORD IMMEDIATELY!");
            log.info("=".repeat(50));
        } else {
            log.info("Super admin already exists, skipping creation");
        }
    }

    private void createPermissionIfNotExists(String name, String description, Permission.PermissionCategory category) {
        if (!permissionRepository.existsByName(name)) {
            Permission permission = new Permission();
            permission.setName(name);
            permission.setDescription(description);
            permission.setCategory(category);
            permissionRepository.save(permission);
        }
    }
}
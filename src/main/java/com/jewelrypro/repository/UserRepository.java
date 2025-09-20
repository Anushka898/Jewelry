package com.jewelrypro.repository;

import com.jewelrypro.entity.User;
import com.jewelrypro.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmailAndTenant(String email, Tenant tenant);
    
    Optional<User> findByEmailAndTenantTenantId(String email, String tenantId);
    
    Optional<User> findByEmail(String email); // For super admin
    
    List<User> findByTenant(Tenant tenant);
    
    List<User> findByTenantTenantId(String tenantId);
    
    @Query("SELECT u FROM User u WHERE u.isSuperAdmin = true")
    List<User> findSuperAdmins();
    
    @Query("SELECT u FROM User u WHERE u.tenant.tenantId = :tenantId AND u.status = 'ACTIVE'")
    List<User> findActiveUsersByTenant(@Param("tenantId") String tenantId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.tenant.tenantId = :tenantId")
    long countUsersByTenant(@Param("tenantId") String tenantId);
    
    boolean existsByEmailAndTenant(String email, Tenant tenant);
    
    boolean existsByEmailAndTenantIsNull(String email); // For super admin check
}
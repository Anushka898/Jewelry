package com.jewelrypro.repository;

import com.jewelrypro.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    
    Optional<Tenant> findByTenantId(String tenantId);
    
    Optional<Tenant> findByEmail(String email);
    
    List<Tenant> findByStatus(Tenant.TenantStatus status);
    
    @Query("SELECT t FROM Tenant t WHERE t.status = 'ACTIVE' ORDER BY t.createdAt DESC")
    List<Tenant> findActiveTenants();
    
    @Query("SELECT COUNT(t) FROM Tenant t WHERE t.status = 'ACTIVE'")
    long countActiveTenants();
    
    @Query("SELECT t FROM Tenant t WHERE t.subscriptionEndDate < CURRENT_TIMESTAMP")
    List<Tenant> findExpiredSubscriptions();
    
    boolean existsByTenantId(String tenantId);
    
    boolean existsByEmail(String email);
}
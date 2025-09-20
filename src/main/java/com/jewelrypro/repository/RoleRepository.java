package com.jewelrypro.repository;

import com.jewelrypro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
    
    List<Role> findByRoleType(Role.RoleType roleType);
    
    @Query("SELECT r FROM Role r WHERE r.roleType = 'TENANT'")
    List<Role> findTenantRoles();
    
    @Query("SELECT r FROM Role r WHERE r.roleType = 'SUPER_ADMIN'")
    List<Role> findSuperAdminRoles();
    
    boolean existsByName(String name);
}
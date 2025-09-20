package com.jewelrypro.repository;

import com.jewelrypro.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Optional<Permission> findByName(String name);
    
    List<Permission> findByCategory(Permission.PermissionCategory category);
    
    boolean existsByName(String name);
}
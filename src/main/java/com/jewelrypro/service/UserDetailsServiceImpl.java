package com.jewelrypro.service;

import com.jewelrypro.entity.User;
import com.jewelrypro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);
        
        // First try to find super admin user
        User user = userRepository.findByEmail(email)
            .orElse(null);
            
        if (user == null) {
            log.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        log.debug("Found user: {} (Super Admin: {})", user.getEmail(), user.getIsSuperAdmin());
        return user;
    }
    
    public UserDetails loadUserByEmailAndTenant(String email, String tenantId) throws UsernameNotFoundException {
        log.debug("Loading user by email: {} and tenantId: {}", email, tenantId);
        
        User user = userRepository.findByEmailAndTenantTenantId(email, tenantId)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User not found with email: " + email + " in tenant: " + tenantId));
        
        log.debug("Found tenant user: {}", user.getEmail());
        return user;
    }
}
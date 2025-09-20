package com.jewelrypro.service;

import com.jewelrypro.dto.auth.LoginRequest;
import com.jewelrypro.dto.auth.LoginResponse;
import com.jewelrypro.entity.User;
import com.jewelrypro.repository.UserRepository;
import com.jewelrypro.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse authenticate(LoginRequest request) {
        log.info("Authentication attempt for email: {}, tenantId: {}", 
                 request.getEmail(), request.getTenantId());

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        
        // Validate tenant access for non-super admin users
        if (!Boolean.TRUE.equals(user.getIsSuperAdmin())) {
            if (request.getTenantId() == null) {
                throw new RuntimeException("Tenant ID is required for tenant users");
            }
            
            if (user.getTenant() == null || !user.getTenant().getTenantId().equals(request.getTenantId())) {
                throw new RuntimeException("Invalid tenant access");
            }
        }

        // Update last login information
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        String tenantId = user.getTenant() != null ? user.getTenant().getTenantId() : null;
        String token = jwtUtil.generateToken(user.getEmail(), tenantId, 
                                           Boolean.TRUE.equals(user.getIsSuperAdmin()));

        // Create response
        LoginResponse response = new LoginResponse(token, 86400000L); // 24 hours
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setFullName(user.getFullName());
        response.setInitials(user.getInitials());
        response.setIsSuperAdmin(Boolean.TRUE.equals(user.getIsSuperAdmin()));
        
        if (user.getTenant() != null) {
            response.setTenantId(user.getTenant().getTenantId());
            response.setCompanyName(user.getTenant().getCompanyName());
        }

        // Set roles and permissions
        response.setRoles(user.getRoles().stream()
            .map(role -> role.getName())
            .toArray(String[]::new));

        response.setPermissions(user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toArray(String[]::new));

        log.info("Authentication successful for user: {} (Super Admin: {})", 
                 user.getEmail(), user.getIsSuperAdmin());

        return response;
    }

    public User getCurrentUser(String email, String tenantId) {
        if (tenantId != null) {
            return userRepository.findByEmailAndTenantTenantId(email, tenantId)
                .orElse(null);
        } else {
            // Super admin user
            return userRepository.findByEmail(email).orElse(null);
        }
    }
}
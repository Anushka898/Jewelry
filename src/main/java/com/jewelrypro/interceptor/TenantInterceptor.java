package com.jewelrypro.interceptor;

import com.jewelrypro.context.TenantContext;
import com.jewelrypro.context.UserContext;
import com.jewelrypro.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Clear any existing context
        TenantContext.clear();
        UserContext.clear();

        // Skip tenant context for public endpoints and super admin endpoints
        String requestPath = request.getRequestURI();
        if (isPublicEndpoint(requestPath)) {
            return true;
        }

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            UserContext.setCurrentUser(user);
            
            // Set tenant context only for non-super admin users
            if (user.getTenant() != null) {
                TenantContext.setTenantId(user.getTenant().getTenantId());
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        // Clean up context after request completion
        TenantContext.clear();
        UserContext.clear();
    }

    private boolean isPublicEndpoint(String requestPath) {
        return requestPath.startsWith("/api/public") ||
               requestPath.startsWith("/api/auth") ||
               requestPath.startsWith("/h2-console") ||
               requestPath.startsWith("/swagger-ui") ||
               requestPath.startsWith("/api-docs") ||
               requestPath.contains("health") ||
               requestPath.contains("actuator");
    }
}
package com.jewelrypro.context;

import com.jewelrypro.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContext {
    
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
    
    public static void setCurrentUser(User user) {
        log.debug("Setting user context: {} ({})", user.getEmail(), 
                 user.getTenant() != null ? user.getTenant().getTenantId() : "SUPER_ADMIN");
        currentUser.set(user);
    }
    
    public static User getCurrentUser() {
        return currentUser.get();
    }
    
    public static void clear() {
        log.debug("Clearing user context");
        currentUser.remove();
    }
    
    public static boolean hasUser() {
        return currentUser.get() != null;
    }
    
    public static boolean isSuperAdmin() {
        User user = getCurrentUser();
        return user != null && Boolean.TRUE.equals(user.getIsSuperAdmin());
    }
    
    public static String getCurrentTenantId() {
        User user = getCurrentUser();
        return user != null && user.getTenant() != null ? user.getTenant().getTenantId() : null;
    }
}
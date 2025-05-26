package com.example.application.config;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class SecurityUtils {

    public static OidcUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            return (OidcUser) authentication.getPrincipal();
        }
        return null;
    }

    public static String getName() {
        OidcUser user = getAuthenticatedUser();
        return user != null ? user.getFullName() : "Invitado";
    }

    public static String getEmail() {
        OidcUser user = getAuthenticatedUser();
        return user != null ? user.getEmail() : "";
    }

    public static String getPictureUrl() {
        OidcUser user = getAuthenticatedUser();
        return user != null ? (String) user.getAttributes().get("picture") : null;
    }

    public static List<String> getRoles() {
        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object rolesClaim = user.getClaims().get("https://myapp.example.com/");

        if (rolesClaim instanceof List<?> rolesList) {
            return rolesList.stream()
                .filter(role -> role instanceof String)
                .map(role -> (String) role)
                .toList();
        }

        return List.of("Invitado");
    }

    public static boolean hasRole(String role) {
        return getRoles().contains(role);
    }
}

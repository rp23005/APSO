package com.example.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(securedEnabled = true) // Enables @Secured
public class MethodSecurityConfig {
    // No additional code needed unless you customize security rules
}
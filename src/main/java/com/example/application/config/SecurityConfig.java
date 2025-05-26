package com.example.application.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/frontend/**",
                                                                "/VAADIN/**",
                                                                "/vaadinServlet/**",
                                                                "/webjars/**",
                                                                "/icons/**",
                                                                "/images/**",
                                                                "/error")
                                                .permitAll()
                                                // Secure all Vaadin internal requests
                                                .requestMatchers(new AntPathRequestMatcher("/**")).authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .defaultSuccessUrl("/", true)
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userAuthoritiesMapper(this.userAuthoritiesMapper())))
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/"))
                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/access-denied"))
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers(
                                                                "/VAADIN/**",
                                                                "/vaadinServlet/**",
                                                                "/**"));

                return http.build();
        }

        private GrantedAuthoritiesMapper userAuthoritiesMapper() {
                return (authorities) -> {
                        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
                        
                        authorities.forEach(authority -> {
                                if ("Admin".equals(authority.getAuthority())) {
                                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                                }else if("User".equals(authority.getAuthority())){
                                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                                }
                        });

                        return mappedAuthorities;
                };
        }
}

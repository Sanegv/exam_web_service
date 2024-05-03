package com.ynov.controle.security;

import com.ynov.controle.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( registry -> {
                    registry
                            .requestMatchers("users/login")
                            .permitAll()
                            .requestMatchers("/users/**")
                            .hasRole(Role.RoleEnum.USER.name())
                            .requestMatchers("/api/**")
                            .hasRole(Role.RoleEnum.USER.name())
                            .requestMatchers("/admin/login")
                            .permitAll()
                            .requestMatchers("/admin/**")
                            .hasRole(Role.RoleEnum.ADMIN.name())
                            .anyRequest()
                            .permitAll();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
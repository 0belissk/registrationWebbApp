package com.paulcodes.registrationSMN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (enable in production)
                .authorizeHttpRequests(auth -> auth
                        // Public Endpoints
                        .requestMatchers("/user/**", "/verify/**", "/health").permitAll()
                        // Admin-Specific Endpoints (Role-Based Access)
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Any other endpoint requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Enable form-based login
                .httpBasic(withDefaults()); // Enable basic authentication
        return http.build();
    }

    //sets myself to admin, without having to register in database and then promote myself to admin, thus hardcoding for direct access
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("pquinto347@gmail.com") // Your admin email
                .password(passwordEncoder.encode("22233001725960")) // Encode password using the configured PasswordEncoder
                .roles("ADMIN") // Assign the ADMIN role
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

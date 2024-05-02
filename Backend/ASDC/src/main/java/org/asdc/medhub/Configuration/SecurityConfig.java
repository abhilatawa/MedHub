package org.asdc.medhub.Configuration;

import org.asdc.medhub.Service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * JwtAuth filter instance
     */
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * User detail service instance
     */
    private UserDetailServiceImp userDetailService;

    /**
     * Parameterized constructor
     * @param jwtAuthFilter - jwt auth filter injection
     * @param userDetailService - user details service injection
     */
    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailServiceImp userDetailService){
        this.jwtAuthFilter=jwtAuthFilter;
        this.userDetailService=userDetailService;
    }


    //region PUBLIC METHODS
    /**
     * Filter chain method
     * @param http - http security parameter
     * @return SecurityFilterChain
     * @throws Exception - unknown exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Password encoder factory method
     * @return Password encoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager factory class
     * @param configuration  - AuthenticationConfiguration class
     * @return AuthenticationManage instance
     * @throws Exception - throws unknown instance
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    //endregion
}


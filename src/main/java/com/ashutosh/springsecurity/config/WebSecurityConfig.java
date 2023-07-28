package com.ashutosh.springsecurity.config;

import com.ashutosh.springsecurity.filters.JwtTokenFilter;
import com.ashutosh.springsecurity.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserServiceImpl userDetailsService;

    private final JwtTokenFilter jwtAuthenticationFilter;
    public WebSecurityConfig(JwtTokenFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/home","/register","/saveUser").permitAll()
                                .requestMatchers("/admin").hasAuthority("Admin")
                                .requestMatchers("/mgr").hasAuthority("Manager")
                                .requestMatchers("/emp").hasAuthority("Employee")
                                .requestMatchers("/hr").hasAuthority("HR")
                                .requestMatchers("/common").hasAnyAuthority("Employeee,Manager,Admin")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider());

        return httpSecurity.build();
    }
}

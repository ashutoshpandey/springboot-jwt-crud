package com.ashutosh.springsecurity.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    boolean isTokenValid(String token);
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    String resolveToken(HttpServletRequest request);
}

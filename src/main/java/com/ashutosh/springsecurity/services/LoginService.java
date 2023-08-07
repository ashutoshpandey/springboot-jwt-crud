package com.ashutosh.springsecurity.services;

import com.ashutosh.springsecurity.models.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class LoginService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String doLogin(LoginModel loginModel) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginModel.getEmail(), loginModel.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getEmail());

        return jwtService.generateToken(userDetails);
    }
}

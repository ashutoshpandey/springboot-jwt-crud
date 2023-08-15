package com.ashutosh.springsecurity.controllers;

import com.ashutosh.springsecurity.models.response.ApiResponse;
import com.ashutosh.springsecurity.models.request.LoginRequest;
import com.ashutosh.springsecurity.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ApiResponse saveUser(@RequestBody LoginRequest loginModel) throws Exception{
        return new ApiResponse(true, loginService.doLogin(loginModel));
    }
}

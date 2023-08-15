package com.ashutosh.springsecurity.services;

import com.ashutosh.springsecurity.models.request.LoginRequest;

public interface LoginService {
    public String doLogin(LoginRequest loginModel) throws Exception;
}

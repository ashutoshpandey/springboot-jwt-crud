package com.ashutosh.springsecurity.models.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

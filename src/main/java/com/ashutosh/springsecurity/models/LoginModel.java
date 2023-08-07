package com.ashutosh.springsecurity.models;

import lombok.Data;

@Data
public class LoginModel {
    private String email;
    private String password;
}

package com.ashutosh.springsecurity.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Provide valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}

package com.ashutosh.springsecurity.models.request;

import lombok.Data;

@Data
public class FilterUserRequest {
    private String name;
    private String email;
}

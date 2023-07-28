package com.ashutosh.springsecurity.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private Object data;
}

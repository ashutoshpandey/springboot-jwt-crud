package com.ashutosh.springsecurity.services;

import com.ashutosh.springsecurity.models.ApiResponse;
import com.ashutosh.springsecurity.models.User;

import java.util.List;

public interface UserService {
    ApiResponse saveUser(User user);

    ApiResponse getUserById(Long userId);

    ApiResponse getAllUsers();

    ApiResponse updateUser(User user);

    ApiResponse deleteUser(Long userId);
}

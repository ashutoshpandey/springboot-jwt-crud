package com.ashutosh.springsecurity.services;

import com.ashutosh.springsecurity.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    Long deleteUser(Long userId);
}

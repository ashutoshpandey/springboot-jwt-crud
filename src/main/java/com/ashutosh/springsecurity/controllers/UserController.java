package com.ashutosh.springsecurity.controllers;

import com.ashutosh.springsecurity.exceptions.RecordNotFoundException;
import com.ashutosh.springsecurity.models.request.FilterUserRequest;
import com.ashutosh.springsecurity.models.response.ApiResponse;
import com.ashutosh.springsecurity.models.User;
import com.ashutosh.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // build create User REST API
    @PostMapping
    public ApiResponse saveUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return new ApiResponse(true, savedUser);
    }

    // get user by id
    // /api/users/1
    @GetMapping("{id}")
    public ApiResponse getUserById(@PathVariable("id") Long userId){
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User not found with ID: " + userId);
        } else {
            return new ApiResponse(true, optionalUser.get());
        }
    }

    // Get All Users REST API
    // /api/users
    @PostMapping("/filter")
    public ApiResponse filterUsers(@RequestBody FilterUserRequest request){
        List<User> users = userService.filterUsers(request);
        return new ApiResponse(true, users);
    }

    // Update User
    @PutMapping("{id}")
    // /api/users/1
    public ApiResponse updateUser(@PathVariable("id") Long userId,
                                           @RequestBody User user){
        user.setId(userId);
        User updatedUser = userService.updateUser(user);

        return new ApiResponse(true, updatedUser);
    }

    // Delete User by id
    @DeleteMapping("{id}")
    public ApiResponse deleteUser(@PathVariable("id") Long userId){
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User not found with ID: " + userId);
        } else {
            userService.deleteUser(userId);
            return new ApiResponse(true, "User successfully deleted!");
        }
    }
}
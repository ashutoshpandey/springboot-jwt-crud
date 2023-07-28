package com.ashutosh.springsecurity.services.impl;

import com.ashutosh.springsecurity.exceptions.RecordNotFoundException;
import com.ashutosh.springsecurity.models.ApiResponse;
import com.ashutosh.springsecurity.models.User;
import com.ashutosh.springsecurity.repository.UserRepository;
import com.ashutosh.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ApiResponse saveUser(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        user = userRepo.save(user);
        return new ApiResponse(true, user);
    }

    @Override
    public ApiResponse getUserById(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User not found with ID: " + userId);
        } else {
            return new ApiResponse(true, optionalUser.get());
        }
    }

    @Override
    public ApiResponse getAllUsers() {
        return new ApiResponse(true, userRepo.findAll());
    }

    @Override
    public ApiResponse updateUser(User user) {
        Optional<User> existing = userRepo.findById(user.getId());

        if(existing.isEmpty()){
            throw new RecordNotFoundException("User not found with ID: " + user.getId());
        }else {
            User existingUser = existing.get();

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            String password = user.getPassword();
            if (null != password) {
                String encodedPassword = passwordEncoder.encode(password);
                existingUser.setPassword(encodedPassword);
            }

            existingUser = userRepo.save(existingUser);
            return new ApiResponse(true, existingUser);
        }
    }

    @Override
    public ApiResponse deleteUser(Long userId) {
        Optional<User> existing = userRepo.findById(userId);

        if(existing.isEmpty()){
            throw new RecordNotFoundException("User not found with ID: " + userId);
        }else {
            userRepo.deleteById(userId);
            return new ApiResponse(true, userId);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<User> opt = userRepo.findUserByEmail(email);

        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +email +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toSet())
            );
        }
    }
}

package com.ashutosh.springsecurity.services.impl;

import com.ashutosh.springsecurity.exceptions.RecordNotFoundException;
import com.ashutosh.springsecurity.models.User;
import com.ashutosh.springsecurity.models.request.FilterUserRequest;
import com.ashutosh.springsecurity.repository.UserRepository;
import com.ashutosh.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public List<User> filterUsers(FilterUserRequest request) {
        List<User> users;

        if (request.getName() != null && request.getEmail() != null) {
            users = userRepo.getUsersByNameAndEmail(request.getName(), request.getEmail());
        } else if (request.getEmail() != null) {
            users = userRepo.getUsersByEmail(request.getEmail());
        } else if (request.getName() != null) {
            users = userRepo.getUsersByName(request.getName());
        } else {
            users = new ArrayList<>();
        }

        return users;
    }

    @Override
    public User updateUser(User user) {
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

            return userRepo.save(existingUser);
        }
    }

    @Override
    public Long deleteUser(Long userId) {
        Optional<User> existing = userRepo.findById(userId);

        if(existing.isEmpty()){
            throw new RecordNotFoundException("User not found with ID: " + userId);
        }else {
            userRepo.deleteById(userId);
            return userId;
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
                            .map(role-> new SimpleGrantedAuthority(role.getRole().name()))
                            .collect(Collectors.toSet())
            );
        }
    }
}

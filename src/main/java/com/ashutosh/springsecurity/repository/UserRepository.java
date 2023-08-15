package com.ashutosh.springsecurity.repository;

import com.ashutosh.springsecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> getUsersByName(String name);
    List<User> getUsersByEmail(String email);
    Optional<User> findUserByEmail(String email);
    List<User> getUsersByNameAndEmail(String name, String email);
}

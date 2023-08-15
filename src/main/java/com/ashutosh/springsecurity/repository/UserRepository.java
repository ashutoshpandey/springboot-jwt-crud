package com.ashutosh.springsecurity.repository;

import com.ashutosh.springsecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> getUsersByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> getUsersByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% AND u.email LIKE %:email%")
    List<User> getUsersByNameAndEmail(@Param("name") String name, @Param("email") String email);
}

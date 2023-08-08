package com.ashutosh.springsecurity.repository;

import com.ashutosh.springsecurity.enums.ERole;
import com.ashutosh.springsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}

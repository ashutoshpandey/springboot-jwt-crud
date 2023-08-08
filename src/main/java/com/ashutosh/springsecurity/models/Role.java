package com.ashutosh.springsecurity.models;

import com.ashutosh.springsecurity.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole role;

    public Role() {
    }

    public Role(ERole role) {
        this.role = role;
    }
}
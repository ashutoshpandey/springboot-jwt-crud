package com.ashutosh.springsecurity.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    @ToString.Exclude
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="roles")
    private String roles;
}
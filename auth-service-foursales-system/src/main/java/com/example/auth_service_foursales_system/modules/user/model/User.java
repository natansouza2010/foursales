package com.example.auth_service_foursales_system.modules.user.model;

import com.example.auth_service_foursales_system.modules.user.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


}

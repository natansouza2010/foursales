package com.example.auth_service_foursales_system.modules.user.repository;

import com.example.auth_service_foursales_system.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserById(UUID uuid);

    Optional<User> findByEmail(String email);
}

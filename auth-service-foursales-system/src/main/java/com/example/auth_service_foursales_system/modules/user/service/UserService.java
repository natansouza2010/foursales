package com.example.auth_service_foursales_system.modules.user.service;

import com.example.auth_service_foursales_system.config.exception.UserException;
import com.example.auth_service_foursales_system.modules.jwt.service.JwtService;
import com.example.auth_service_foursales_system.modules.user.dto.AuthResponse;
import com.example.auth_service_foursales_system.modules.user.dto.UserRequest;
import com.example.auth_service_foursales_system.modules.user.dto.UserResponse;
import com.example.auth_service_foursales_system.modules.user.model.User;
import com.example.auth_service_foursales_system.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse findByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow( () -> new UserException("User not found"));
        return UserResponse.of(user);
    }

    public AuthResponse getAccessToken(UserRequest userRequest) {
        var email = userRequest.getEmail();
        var password = userRequest.getPassword();
        var authUser = userRepository.findByEmail(email).orElseThrow( () -> new UserException("User not found"));
        validatePassword(password, authUser);

        var accessToken = jwtService.generateToken(
                authUser.getEmail(),
                authUser.getId(),
                authUser.getName(),
                authUser.getRole()
        );

        return new AuthResponse(accessToken);
    }


    private void validatePassword(String password, User authUser) {
        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            throw new UserException("Password doesn't match.");
        }
    }


}

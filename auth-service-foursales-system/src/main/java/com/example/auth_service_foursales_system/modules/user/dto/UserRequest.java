package com.example.auth_service_foursales_system.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class UserRequest {
    @NotNull(message = "Email must be provided.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Password must be provided.")
    private String password;

}

package com.example.auth_service_foursales_system.modules.user.dto;

import com.example.auth_service_foursales_system.modules.user.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String name;
    private String email;

    public static UserResponse of(User category) {
        var response = new UserResponse();
        BeanUtils.copyProperties(category,response);
        return response;
    }
}
package com.example.auth_service_foursales_system.modules.user.controller;


import com.example.auth_service_foursales_system.modules.user.dto.AuthResponse;
import com.example.auth_service_foursales_system.modules.user.dto.UserRequest;
import com.example.auth_service_foursales_system.modules.user.dto.UserResponse;
import com.example.auth_service_foursales_system.modules.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/user/auth")
    public AuthResponse getAcessToken(@RequestBody UserRequest userRequest) {
        return userService.getAccessToken(userRequest);
    }


    @GetMapping("/user/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }


}

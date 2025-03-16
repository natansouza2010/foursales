package com.example.auth_service_foursales_system.modules.user.data;

import com.example.auth_service_foursales_system.modules.user.enums.Role;
import com.example.auth_service_foursales_system.modules.user.model.User;
import com.example.auth_service_foursales_system.modules.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadInitialData();
        }
    }

    private void loadInitialData() {

        User admin = new User();
        admin.setEmail("admin@foursales.com");
        admin.setName("Admin");
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode("admin123"));
        userRepository.save(admin);


        User normalUser = new User();
        normalUser.setEmail("user@foursales.com");
        normalUser.setName("User");
        normalUser.setRole(Role.USER);
        normalUser.setPassword(passwordEncoder.encode("user123"));
        userRepository.save(normalUser);

        System.out.println("Load users complete.");
    }
}

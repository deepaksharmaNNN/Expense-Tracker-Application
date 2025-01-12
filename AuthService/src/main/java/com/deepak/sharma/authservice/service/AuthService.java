package com.deepak.sharma.authservice.service;

import com.deepak.sharma.authservice.entity.Role;
import com.deepak.sharma.authservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(String username, String email, String password, Set<String> roleNames) {
        if (userService.findByUsername(username).isPresent() || userService.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleService.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();

        return userService.save(user);
    }
}

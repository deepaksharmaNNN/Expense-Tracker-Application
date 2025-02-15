package com.deepak.sharma.authservice.service;

import com.deepak.sharma.authservice.entity.Role;
import com.deepak.sharma.authservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}

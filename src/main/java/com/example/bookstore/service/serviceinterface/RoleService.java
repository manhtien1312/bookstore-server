package com.example.bookstore.service.serviceinterface;

import com.example.bookstore.model.ERole;
import com.example.bookstore.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(ERole roleName);
}

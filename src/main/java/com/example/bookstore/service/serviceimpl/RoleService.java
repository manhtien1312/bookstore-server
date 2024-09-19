package com.example.bookstore.service.serviceimpl;

import com.example.bookstore.model.ERole;
import com.example.bookstore.model.Role;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.service.serviceinterface.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}

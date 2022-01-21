package com.example.meodihia_backend.service.role;

import com.example.meodihia_backend.model.Role;
import com.example.meodihia_backend.model.RoleName;
import com.example.meodihia_backend.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}

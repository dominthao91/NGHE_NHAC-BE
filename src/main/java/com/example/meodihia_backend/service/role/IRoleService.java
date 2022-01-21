package com.example.meodihia_backend.service.role;

import com.example.meodihia_backend.model.Role;
import com.example.meodihia_backend.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
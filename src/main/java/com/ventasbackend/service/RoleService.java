package com.ventasbackend.service;

import com.ventasbackend.entity.enums.ERole;
import com.ventasbackend.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleType(ERole roleType);
}
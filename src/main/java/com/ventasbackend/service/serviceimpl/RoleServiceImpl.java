package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.entity.ERole;
import com.ventasbackend.entity.Role;
import com.ventasbackend.repository.RoleRepository;
import com.ventasbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRoleType(ERole roleType) {
        return roleRepository.findByRoleType(roleType);
    }
}
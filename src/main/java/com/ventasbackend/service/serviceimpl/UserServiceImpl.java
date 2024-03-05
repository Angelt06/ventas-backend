package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.entity.ERole;
import com.ventasbackend.entity.Role;
import com.ventasbackend.entity.User;
import com.ventasbackend.exceptions.RoleNotFoundException;
import com.ventasbackend.repository.RoleRepository;
import com.ventasbackend.repository.UserRepository;
import com.ventasbackend.service.UserService;
import com.ventasbackend.mapper.UserMapper; // Importa tu clase UserMapper aquí
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userMapper::userToUserDTO);
    }

    @Override
    public Optional<UserDTO> findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(userMapper::userToUserDTO);
    }
    @Override
    @Transactional
    public UserDTO saveUser(UserDTO user) {
        User userEntity = userMapper.userDTOToUser(user);
        if(user.getId() == null) {
            ERole eRole = hasUser();
            Role role = roleRepository.findByRoleType(eRole)
                    .orElseThrow(() -> new RoleNotFoundException("Role not found"));

            userEntity.setRole(role);
        }else {
            User userOld = userRepository.findById(user.getId()).orElse(null);
            userEntity.setRole(userOld.getRole());
        }
        User savedUser = userRepository.save(userEntity);

        return userMapper.userToUserDTO(savedUser);
    }

    private ERole hasUser() {
        boolean hasUsers = userRepository.count() > 0;

        ERole roleType = hasUsers ? ERole.CUSTOMER : ERole.ADMINISTRATOR;
        return roleType;
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
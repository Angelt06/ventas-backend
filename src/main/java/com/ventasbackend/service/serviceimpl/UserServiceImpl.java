package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.entity.enums.ERole;
import com.ventasbackend.entity.Role;
import com.ventasbackend.entity.User;
import com.ventasbackend.exceptions.ApiRequestException;
import com.ventasbackend.repository.RoleRepository;
import com.ventasbackend.repository.UserRepository;
import com.ventasbackend.service.UserService;
import com.ventasbackend.mapper.UserMapper; // Importa tu clase UserMapper aquí
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user == null) ? null : userMapper.userToUserDTO(user);
    }
    @Override
    @Transactional
    public UserDTO saveUser(UserDTO user) {
        logger.info("Inicio del método saveUser()");
        logger.info("Usuario recibido: {}", user);
        User userEntity;
        userEntity = (user.getId() == null)
                ? isCreating(user) :  isUpdating(user);
        logger.info("Usuario creado/actualizado: {}", userEntity);
        User savedUser = userRepository.save(userEntity);
        logger.info("Usuario guardado en la base de datos: {}", savedUser);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return (user == null) ? null : userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return (user == null) ? null : userMapper.userToUserDTO(user);
    }

    private User isUpdating(UserDTO user) {
        User userEntity;
        User userOldRole = userRepository.findById(user.getId()).orElse(null);
        userEntity = userMapper.userDTOToUser(user);
        userEntity.setRole(userOldRole.getRole());
        return userEntity;
    }

    private User isCreating(UserDTO user) {
        User userEntity;
        ERole eRole = roleAsign();
        Role role = roleRepository.findByRoleType(eRole)
                .orElseThrow(() -> new ApiRequestException("Error"));

        userEntity = userMapper.userDTOToUser(user);
        userEntity.setRole(role);
        return userEntity;
    }

    private ERole roleAsign() {
        boolean hasUsers = userRepository.count() > 0;

        ERole roleType = hasUsers ? ERole.CUSTOMER : ERole.ADMINISTRATOR;
        return roleType;
    }
}
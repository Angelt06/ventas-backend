package com.ventasbackend.service;


import com.ventasbackend.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Page<UserDTO> getAllUsers(Pageable pageable);
    Optional<UserDTO> findUserById(Long userId);
    UserDTO saveUser(UserDTO user);
    void deleteUser(Long userId);

}
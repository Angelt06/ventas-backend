package com.ventasbackend.mapper;

import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        // Puedes agregar más asignaciones si es necesario
        return userDTO;
    }

    public User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        // Puedes agregar más asignaciones si es necesario
        return user;
    }
}

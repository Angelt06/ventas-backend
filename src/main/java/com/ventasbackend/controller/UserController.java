package com.ventasbackend.controller;

import com.ventasbackend.dto.AuthRequestDTO;
import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.service.UserService;
import com.ventasbackend.service.serviceimpl.AuthServiceImpl;
import com.ventasbackend.utils.ControllerErrors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final String entiyName = "usuario";

    @Autowired
    AuthServiceImpl authService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/paged/{page}")
    public ResponseEntity<Page<UserDTO>> getAllUsers(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<UserDTO> usersPage = userService.getAllUsers(pageable);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDTO existingUserById;
        try {
            existingUserById = userService.findUserById(id);
            if(existingUserById  == null){
                return ControllerErrors.handleEntityNotFound(id, entiyName );}

        } catch (DataAccessException e) {
           return handleClientError(e);
        }

        return new ResponseEntity<>(existingUserById ,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO,
                                        BindingResult result) {
        String action = "actualizado";
        UserDTO existingUser;
        UserDTO existingUserName;
        UserDTO existingUserEmail;

        if(result.hasErrors()){ return ControllerErrors.handleValidationErrors(result);}

        try {
            existingUserName = userService.findUserByUsername(userDTO.getUsername());
            if (existingUserName != null && (!existingUserName.equals(userDTO.getUsername()))) {
                return ControllerErrors.handleEntityError("El username ya existe");}

            existingUserEmail = userService.findUserByEmail(userDTO.getEmail());
            if (existingUserEmail != null && (!existingUserEmail.equals(userDTO.getEmail()))) {
                return ControllerErrors.handleEntityError("El email ya existe");}

            if(userService.findUserById(id) == null) {
                return ControllerErrors.handleEntityNotFound(id, entiyName );
            }
            userDTO.setId(id);
            existingUser = userService.saveUser(userDTO);

        } catch (DataAccessException e) {
           return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingUser, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String action = "eliminado";
        UserDTO existingUser;

        try {
            existingUser = userService.findUserById(id);
            if(existingUser == null){return ControllerErrors.handleEntityNotFound(id, entiyName);}
            userService.deleteUser(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingUser, action), HttpStatus.OK);

    }

    private Map<String, Object> handleSuccessResponse(UserDTO userDTO, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El usuario ha sido " + action + " con éxito");
        response.put("usuario", userDTO);
        return response;
    }
    private ResponseEntity<?> handleClientError(DataAccessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al ejecutar consulta en la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
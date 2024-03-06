package com.ventasbackend.controller;
import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.service.UserService;
import com.ventasbackend.utils.ControllerUtils;
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
    private UserDTO user = null;

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

        try {
             user = userService.findUserById(id).orElse(null);
            if(user == null){return ControllerUtils.handleEntityNotFound(id, entiyName);}

        } catch (DataAccessException e) {
           return handleClientError(e);
        }

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        String action = "creado";

        if(result.hasErrors()){ return ControllerUtils.handleValidationErrors(result);}

        try {
           user = userService.saveUser(userDTO);

        } catch (DataAccessException e) {
           return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(user, action), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO,
                                        BindingResult result) {
        String action = "actualizado";

        if(result.hasErrors()){ return ControllerUtils.handleValidationErrors(result);}

        user = userService.findUserById(id).orElse(null);
        if(user == null){return ControllerUtils.handleEntityNotFound(id, entiyName);}

        try {
            userDTO.setId(id);
           user = userService.saveUser(userDTO);

        } catch (DataAccessException e) {
           return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(user, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String action = "eliminado";
        user = userService.findUserById(id).orElse(null);
        if(user == null){return ControllerUtils.handleEntityNotFound(id, entiyName);}
        try {
            userService.deleteUser(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(user, action), HttpStatus.OK);

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
package com.ventasbackend.controller;

import com.ventasbackend.dto.AuthRequestDTO;
import com.ventasbackend.dto.UserDTO;
import com.ventasbackend.dto.response.AuthResponseDTO;
import com.ventasbackend.service.UserService;
import com.ventasbackend.service.serviceimpl.AuthServiceImpl;
import com.ventasbackend.service.serviceimpl.UserServiceImpl;
import com.ventasbackend.utils.ControllerErrors;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        return new ResponseEntity<>(authService.login(authRequestDTO), HttpStatus.OK);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        String action = "creado";
        UserDTO existingUser;

        if(result.hasErrors()){ return ControllerErrors.handleValidationErrors(result);}

        try {
            if (userService.findUserByUsername(userDTO.getUsername()) != null) {
                return ControllerErrors.handleEntityError("El username ya existe");}

            if (userService.findUserByEmail(userDTO.getEmail()) != null) {
                return ControllerErrors.handleEntityError("El email ya existe");}
            existingUser = userService.saveUser(userDTO);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingUser, action), HttpStatus.CREATED);
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

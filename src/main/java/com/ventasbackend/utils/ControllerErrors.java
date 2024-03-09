package com.ventasbackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerErrors {

    public static ResponseEntity<?> handleValidationErrors(BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(error -> "El campo '" + error.getField() + "': " + error.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity<?> handleEntityNotFound(Long id, String entityName) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El" + entityName + " con ID " + id + " no existe en la base de datos");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<?> handleEntityError(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", mensaje);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

package com.ventasbackend.controller;

import com.ventasbackend.dto.PaymentDTO;
import com.ventasbackend.service.PaymentService;
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
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final String entityName = "pago";

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/paged/{page}")
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10); // Tamaño de página predeterminado: 10
        Page<PaymentDTO> paymentsPage = paymentService.getAllPayments(pageable);
        return new ResponseEntity<>(paymentsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        PaymentDTO existingPayment;
        try {
            existingPayment = paymentService.findPaymentById(id);
            if (existingPayment == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }

        } catch (DataAccessException e) {
            return handleClientError(e);
        }

        return new ResponseEntity<>(existingPayment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDTO paymentDTO, BindingResult result) {
        String action = "creado";
        PaymentDTO existingPayment;

        if (result.hasErrors()) {
            return ControllerErrors.handleValidationErrors(result);
        }

        try {
            existingPayment = paymentService.savePayment(paymentDTO);
        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingPayment, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        String action = "eliminado";
        PaymentDTO existingPayment;

        try {
            existingPayment = paymentService.findPaymentById(id);
            if (existingPayment == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }
            paymentService.deletePayment(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingPayment, action), HttpStatus.OK);
    }

    private Map<String, Object> handleSuccessResponse(PaymentDTO paymentDTO, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El " + entityName + " ha sido " + action + " con éxito");
        response.put(entityName, paymentDTO);
        return response;
    }

    private ResponseEntity<?> handleClientError(DataAccessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al ejecutar consulta en la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
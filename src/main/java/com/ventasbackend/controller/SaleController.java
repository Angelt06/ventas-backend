package com.ventasbackend.controller;

import com.ventasbackend.dto.SaleDTO;
import com.ventasbackend.service.SaleService;
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
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;
    private final String entityName = "venta";

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<SaleDTO> sales = saleService.getAllSales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/paged/{page}")
    public ResponseEntity<Page<SaleDTO>> getAllSales(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<SaleDTO> salesPage = saleService.getAllSales(pageable);
        return new ResponseEntity<>(salesPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        SaleDTO existingSaleById;
        try {
            existingSaleById = saleService.findSaleById(id);
            if (existingSaleById == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }

        } catch (DataAccessException e) {
            return handleClientError(e);
        }

        return new ResponseEntity<>(existingSaleById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSale(@Valid @RequestBody SaleDTO saleDTO, BindingResult result) {
        String action = "creada";
        SaleDTO existingSale;

        if (result.hasErrors()) {
            return ControllerErrors.handleValidationErrors(result);
        }

        try {
            existingSale = saleService.saveSale(saleDTO);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingSale, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        String action = "eliminada";
        SaleDTO existingSale;

        try {
            existingSale = saleService.findSaleById(id);
            if (existingSale == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }
            saleService.deleteSale(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingSale, action), HttpStatus.OK);
    }

    private Map<String, Object> handleSuccessResponse(SaleDTO saleDTO, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "La " + entityName + " ha sido " + action + " con éxito");
        response.put(entityName, saleDTO);
        return response;
    }

    private ResponseEntity<?> handleClientError(DataAccessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al ejecutar consulta en la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

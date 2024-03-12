package com.ventasbackend.controller;

import com.ventasbackend.dto.SaleDetailDTO;
import com.ventasbackend.entity.Product;
import com.ventasbackend.service.SaleDetailService;
import com.ventasbackend.utils.ControllerErrors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailController {

    private final SaleDetailService saleDetailService;
    private final String entityName = "detalle de venta";

    @Autowired
    public SaleDetailController(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDetailDTO>> getAllSaleDetails() {
        List<SaleDetailDTO> saleDetails = saleDetailService.getAllSaleDetails();
        return new ResponseEntity<>(saleDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleDetailById(@PathVariable Long id) {
        SaleDetailDTO existingSaleDetailById;
        try {
            existingSaleDetailById = saleDetailService.findSaleDetailById(id);
            if (existingSaleDetailById == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }

        } catch (DataAccessException e) {
            return handleClientError(e);
        }

        return new ResponseEntity<>(existingSaleDetailById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSaleDetail(@Valid @RequestBody SaleDetailDTO saleDetailDTO, BindingResult result) {
        String action = "creado";
        SaleDetailDTO existingSaleDetail;

        if (result.hasErrors()) {
            return ControllerErrors.handleValidationErrors(result);
        }

        try {
            Product product = saleDetailService.findProductById(saleDetailDTO.getProduct().getId());
            saleDetailDTO.setProduct(product);
            existingSaleDetail = saleDetailService.saveSaleDetail(saleDetailDTO);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingSaleDetail, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSaleDetail(@PathVariable Long id) {
        String action = "eliminado";
        SaleDetailDTO existingSaleDetail;

        try {
            existingSaleDetail = saleDetailService.findSaleDetailById(id);
            if (existingSaleDetail == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }
            saleDetailService.deleteSaleDetail(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingSaleDetail, action), HttpStatus.OK);
    }

    private Map<String, Object> handleSuccessResponse(SaleDetailDTO saleDetailDTO, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El " + entityName + " ha sido " + action + " con éxito");
        response.put(entityName, saleDetailDTO);
        return response;
    }

    private ResponseEntity<?> handleClientError(DataAccessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al ejecutar consulta en la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

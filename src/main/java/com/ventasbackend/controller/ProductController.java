package com.ventasbackend.controller;

import com.ventasbackend.dto.ProductDTO;
import com.ventasbackend.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final String entityName = "producto";

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/paged/{page}")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ProductDTO> productsPage = productService.getAllProducts(pageable);
        return new ResponseEntity<>(productsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDTO existingProductById;
        try {
            existingProductById = productService.findProductById(id);
            if (existingProductById == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }

        } catch (DataAccessException e) {
            return handleClientError(e);
        }

        return new ResponseEntity<>(existingProductById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        String action = "creado";
        ProductDTO existingProduct;

        if (result.hasErrors()) {
            return ControllerErrors.handleValidationErrors(result);
        }

        try {
            if (productService.findProductByName(productDTO.getName()) != null) {
                return ControllerErrors.handleEntityError("El Producto ya existe");}
            existingProduct = productService.saveProduct(productDTO);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingProduct, action), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductDTO productDTO,
                                           BindingResult result) {
        String action = "actualizado";
        ProductDTO existingProduct;
        ProductDTO existingProductName;

        if (result.hasErrors()) {
            return ControllerErrors.handleValidationErrors(result);
        }

        try {
            existingProductName = productService.findProductByName(productDTO.getName());
            if (existingProductName != null
                    && (!existingProductName.getName().equals(productDTO.getName()))) {
                return ControllerErrors.handleEntityError("El nombre del producto ya existe");
            }
            if (productService.findProductById(id) == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }
            productDTO.setId(id);
            existingProduct = productService.saveProduct(productDTO);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingProduct, action), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String action = "eliminado";
        ProductDTO existingProduct;

        try {
            existingProduct = productService.findProductById(id);
            if (existingProduct == null) {
                return ControllerErrors.handleEntityNotFound(id, entityName);
            }
            productService.deleteProduct(id);

        } catch (DataAccessException e) {
            return handleClientError(e);
        }
        return new ResponseEntity<>(handleSuccessResponse(existingProduct, action), HttpStatus.OK);
    }

    private Map<String, Object> handleSuccessResponse(ProductDTO productDTO, String action) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El " + entityName + " ha sido " + action + " con éxito");
        response.put(entityName, productDTO);
        return response;
    }

    private ResponseEntity<?> handleClientError(DataAccessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al ejecutar consulta en la base de datos");
        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

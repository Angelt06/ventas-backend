package com.ventasbackend.service;

import com.ventasbackend.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductDTO findProductByName(String name);
    List<ProductDTO> getAllProducts();
    Page<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO findProductById(Long productId);
    ProductDTO saveProduct(ProductDTO product);
    void deleteProduct(Long productId);
}

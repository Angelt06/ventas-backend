package com.ventasbackend.service;

import com.ventasbackend.dto.SaleDTO;
import com.ventasbackend.entity.Product;
import com.ventasbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {
    List<SaleDTO> getAllSales();
    Page<SaleDTO> getAllSales(Pageable pageable);
    SaleDTO findSaleById(Long saleId);
    SaleDTO saveSale(SaleDTO sale);
    void deleteSale(Long saleId);
}

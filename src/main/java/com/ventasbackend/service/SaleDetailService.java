package com.ventasbackend.service;

import com.ventasbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import com.ventasbackend.dto.SaleDetailDTO;

public interface SaleDetailService {
    List<SaleDetailDTO> getAllSaleDetails();
    SaleDetailDTO findSaleDetailById(Long saleDetailId);
    SaleDetailDTO saveSaleDetail(SaleDetailDTO saleDetail);
    void deleteSaleDetail(Long saleDetailId);
    Product findProductById(Long productId);
}
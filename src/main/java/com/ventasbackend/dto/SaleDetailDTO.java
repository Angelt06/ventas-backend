package com.ventasbackend.dto;

import com.ventasbackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetailDTO {
    private Long id;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private Product product;
}
package com.ventasbackend.dto;

import com.ventasbackend.entity.enums.ESaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {
    private Long id;
    private LocalDateTime saleDate;
    private double total;
    private Long userId;
    private ESaleStatus saleStatus;
    private List<SaleDetailDTO> saleDetails;
}
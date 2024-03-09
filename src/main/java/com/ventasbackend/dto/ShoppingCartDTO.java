package com.ventasbackend.dto;

import com.ventasbackend.entity.enums.ECartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {
    private Long id;
    private int totalQuantity;
    private double totalPrice;
    private ECartStatus statusCart;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdate;
    private Long userId;
}

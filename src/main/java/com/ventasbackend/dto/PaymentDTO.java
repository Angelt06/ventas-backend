package com.ventasbackend.dto;

import com.ventasbackend.entity.enums.EPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private double amount;
    private String paymentMethod;
    private EPaymentStatus status;
    private LocalDateTime paymentDate;
    private String description;
    private Long saleId;
}

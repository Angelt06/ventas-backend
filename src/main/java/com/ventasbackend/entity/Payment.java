package com.ventasbackend.entity;

import com.ventasbackend.entity.enums.EPaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto del pago no puede ser nulo")
    @Positive(message = "El monto del pago debe ser un número positivo")
    private Double amount;

    @NotBlank(message = "El método de pago no puede estar vacío")
    @Size(max = 255, message = "El método de pago no puede exceder los 255 caracteres")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPaymentStatus status;

    private LocalDateTime paymentDate;

    @Size(max = 255, message = "La descripción del pago no puede exceder los 255 caracteres")
    private String description;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

}
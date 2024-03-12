package com.ventasbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "sale_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Positive(message = "La cantidad debe ser un número positivo")
    private int quantity;

    @Column(nullable = false)
    @PositiveOrZero(message = "El precio unitario debe ser un número positivo o cero")
    private double unitPrice;

    @Column(nullable = false)
    @PositiveOrZero(message = "El subtotal debe ser un número positivo o cero")
    private double subtotal;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
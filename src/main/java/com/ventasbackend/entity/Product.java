package com.ventasbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 100, message = "El nombre del producto debe tener como máximo 100 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "La descripción del producto no puede estar vacía")
    private String description;

    @Column(nullable = false)
    @PositiveOrZero(message = "El precio del producto debe ser mayor o igual a cero")
    private double price;

    @Column(nullable = false)
    @PositiveOrZero(message = "El stock del producto debe ser mayor o igual a cero")
    private int stock;
}
package com.ventasbackend.entity;

import com.ventasbackend.entity.enums.ECartStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shopping_carts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @PositiveOrZero(message = "La cantidad total de productos en el carrito debe ser un número positivo o cero")
    private int totalQuantity;

    @Column(nullable = false)
    @PositiveOrZero(message = "El precio total de los productos en el carrito debe ser un número positivo o cero")
    private double totalPrice;

    @Column(nullable = false)
    @NotBlank(message = "El estado del carrito de compras no puede estar vacío")
    private ECartStatus statusCart;

    @Column(nullable = false)
    @NotNull(message = "La fecha de creación del carrito de compras no puede ser nula")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @NotNull(message = "La fecha de última actualización del carrito de compras no puede ser nula")
    private LocalDateTime lastUpdate;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private User user;

}
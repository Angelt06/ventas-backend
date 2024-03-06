package com.ventasbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(min = 4, max = 20, message = "El tamaño debe estar entre 4 y 20 caracteres")
    private String username;

    @Column(nullable = false)
    @Min(value = 8, message = "Debe tener al menos 8 caracteres")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "El formato de correo electrónico no es válido")
    private String email;

    @ManyToOne( fetch =  FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
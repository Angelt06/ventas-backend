package com.ventasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ventasbackend.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Puedes agregar métodos de consulta personalizados si es necesario
}
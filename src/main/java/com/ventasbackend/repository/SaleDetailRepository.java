package com.ventasbackend.repository;

import com.ventasbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ventasbackend.entity.SaleDetail;

import java.util.Optional;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findProductSaleDetailById(@Param("productId") Long productId);
}
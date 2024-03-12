package com.ventasbackend.repository;

import com.ventasbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ventasbackend.entity.Sale;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findUserSaleById(@Param("userId") Long userId);
}
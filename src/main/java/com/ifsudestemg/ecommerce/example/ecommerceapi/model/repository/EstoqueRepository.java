package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}

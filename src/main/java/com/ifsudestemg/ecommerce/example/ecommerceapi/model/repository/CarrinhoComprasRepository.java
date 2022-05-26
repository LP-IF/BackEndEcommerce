package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CarrinhoCompras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras, Long> {
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}

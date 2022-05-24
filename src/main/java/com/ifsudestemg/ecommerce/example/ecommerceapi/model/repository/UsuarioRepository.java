package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;


import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}

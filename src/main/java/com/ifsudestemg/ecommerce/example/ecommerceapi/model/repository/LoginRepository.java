package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository  extends JpaRepository<Login, Long> {
    Optional<Login> findByLogin(String login);
}

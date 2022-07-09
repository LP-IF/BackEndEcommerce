package com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository;


import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}
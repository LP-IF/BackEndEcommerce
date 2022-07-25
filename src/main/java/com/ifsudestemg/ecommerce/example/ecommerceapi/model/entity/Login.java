package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String senha;
    private String login;
    private boolean admin;
}

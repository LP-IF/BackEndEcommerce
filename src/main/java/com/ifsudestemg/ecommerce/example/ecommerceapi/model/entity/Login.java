package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Login {

    private String senha;
    private String login;
    private boolean admin;
}

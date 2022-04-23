package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    private Integer telefone;
    private Integer senha;
    private String email;
    private String senha;

    @ManyToOne
    private Endereco endereco;
}

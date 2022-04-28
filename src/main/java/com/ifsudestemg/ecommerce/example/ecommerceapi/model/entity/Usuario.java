package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private Integer telefone;
    private String email;
    private String senha;

    @ManyToOne
    private Endereco endereco;


}

package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
    private String nomeProduto;
    private String especificacoes;
    private Double preco;

    @ManyToOne
    private Estoque estoque;

}

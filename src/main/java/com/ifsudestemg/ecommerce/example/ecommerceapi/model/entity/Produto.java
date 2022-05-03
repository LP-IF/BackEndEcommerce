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

    private Long idProduto;
    private String nomeProduto;
    private String especificacoes;
    private Double preco;
    private Integer qtdEstoque;
    private Integer estoqueMin;
    private Integer estoqueMax;
    private Integer pontoRessupri;

    @OneToMany
    private Categoria categoria;

    @OneToMany
    private Tamanho tamanho;

    @OneToMany
    private Cor cor;

}

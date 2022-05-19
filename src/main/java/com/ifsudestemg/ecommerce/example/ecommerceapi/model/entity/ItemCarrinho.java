package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensCarrinho;
    private Double precoTotal;
    private Integer quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private CarrinhoCompras carrinhoCompras;
}

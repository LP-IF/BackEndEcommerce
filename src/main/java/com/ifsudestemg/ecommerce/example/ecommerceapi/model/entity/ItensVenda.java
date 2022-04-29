package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class ItensVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensVenda;
    private String nomeProduto;
    private Double precoUnid;
    private Double precoTotal;
    private Integer quant;
}

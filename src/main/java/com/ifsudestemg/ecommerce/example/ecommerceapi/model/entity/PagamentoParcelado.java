package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagamentoParcelado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PagamentoParcelado;
    private Integer parcelas;
    private Double juros;
}

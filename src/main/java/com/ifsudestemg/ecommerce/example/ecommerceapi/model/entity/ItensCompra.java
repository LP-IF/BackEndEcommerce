package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItensCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensCompra;
    private Double custoUnit;
    private Double custoTotal;
    private Integer quant;
}

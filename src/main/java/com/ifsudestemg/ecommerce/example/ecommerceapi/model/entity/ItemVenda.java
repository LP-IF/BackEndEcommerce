package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double precoUnit;
    private Double precoTotal;
    private Integer quant;

    @OneToOne
    private Pagamento pagamento;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;
}

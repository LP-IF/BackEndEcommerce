package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarrinhoCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinhoCompras;
    private Integer qtdProduto;
    private Boolean freteGratis;
    private Float total;
    private Double precoFrete;

    @ManyToOne
    private Cupom cupom;

    @OneToOne
    private Pagamento pagamento;

}

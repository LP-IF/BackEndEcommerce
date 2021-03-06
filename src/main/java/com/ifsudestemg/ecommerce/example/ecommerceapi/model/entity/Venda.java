package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String estado;
    private Integer estrelasAvaliacao;
    private String comentarioAvaliacao;
    private Integer numeroPedido;
    private String dataVenda;
    private String dataEntrega;

    @ManyToOne
    private Cliente cliente;
}

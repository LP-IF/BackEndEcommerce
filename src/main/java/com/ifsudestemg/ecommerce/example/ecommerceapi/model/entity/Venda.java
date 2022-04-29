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
    private Long idVendas;
    private String estado;
    private Integer estrelasAvaliacao;
    private String comentAvaliacao;
    private String notaFiscal;
    private Integer numeroPedido;
    private Date dataVenda;
}
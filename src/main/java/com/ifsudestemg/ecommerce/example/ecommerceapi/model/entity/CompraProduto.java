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

public class CompraProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCompraProduto;
    private Date dataEntrega;
    private Double custoTotal;

    @ManyToOne
    private Fornecedor fornecedor;
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Fornecedorpj extends Fornecedor {
    private String cnpj;
    private String nomeRepres;
    private String cpfRepres;
    private String emailRepres;
    private Integer telRepres;

    @OneToOne(cascade = CascadeType.ALL)
    private Fornecedor fornecedor;
}

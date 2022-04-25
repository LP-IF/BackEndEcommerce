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

public class Fornecedorpf extends Fornecedor {
    private Date dataNascimento;
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    private Fornecedor fornecedor;
}

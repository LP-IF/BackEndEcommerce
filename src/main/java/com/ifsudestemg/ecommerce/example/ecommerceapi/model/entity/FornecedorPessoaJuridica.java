package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FornecedorPessoaJuridica extends Fornecedor {
    private String cnpj;
    private String nomeRepresentante;
    private String cpfRepresentante;
    private String emailRepresentante;
    private Integer telRepresentante;


}

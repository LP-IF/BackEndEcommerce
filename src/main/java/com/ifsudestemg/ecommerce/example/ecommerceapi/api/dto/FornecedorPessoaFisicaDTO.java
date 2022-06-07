package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class FornecedorPessoaFisicaDTO {
    private Long id;
    private String dataNascimento;
    private String cpf;
    private String nome;
    private Integer telefone;
    private String email;
    private String senha;

    public static FornecedorPessoaFisicaDTO create(FornecedorPessoaFisica fornecedor){
        ModelMapper modelMapper = new ModelMapper();
        FornecedorPessoaFisicaDTO dto = modelMapper.map(fornecedor, FornecedorPessoaFisicaDTO.class);
        return dto;
    }

}


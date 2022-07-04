package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaJuridica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FornecedorPessoaJuridicaDTO {
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String senha;

    private String cnpj;
    private String nomeRepresentante;
    private String cpfRepresentante;
    private String emailRepresentante;
    private String telRepresentante;

    public static FornecedorPessoaJuridicaDTO create(FornecedorPessoaJuridica fornecedor){
        ModelMapper modelMapper = new ModelMapper();
        FornecedorPessoaJuridicaDTO dto = modelMapper.map(fornecedor, FornecedorPessoaJuridicaDTO.class);
        return dto;
    }

}

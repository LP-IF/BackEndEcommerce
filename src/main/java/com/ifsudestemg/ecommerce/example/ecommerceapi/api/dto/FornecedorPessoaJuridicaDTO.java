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
    private Date dataNascimento;
    private String cnpj;
    private String nomeRepresentante;
    private String cpfRepresentante;
    private String emailRepresentante;
    private Integer telRepresentante;
    private String senha;

    public static FornecedorPessoaJuridicaDTO create(FornecedorPessoaJuridica fornecedor){
        ModelMapper modelMapper = new ModelMapper();
        FornecedorPessoaJuridicaDTO dto = modelMapper.map(fornecedor, FornecedorPessoaJuridicaDTO.class);
        return dto;
    }

}

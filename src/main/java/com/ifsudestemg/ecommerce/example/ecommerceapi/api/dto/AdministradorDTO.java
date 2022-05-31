package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Administrador;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdministradorDTO {
    private Long id;
    private Date dataNascimento;
    private String cpf;
    private String nome;
    private Integer telefone;
    private String email;
    private String senha;

    public static AdministradorDTO create(Administrador administrador){
        ModelMapper modelMapper = new ModelMapper();
        AdministradorDTO dto = modelMapper.map(administrador, AdministradorDTO.class);
        return dto;
    }
}

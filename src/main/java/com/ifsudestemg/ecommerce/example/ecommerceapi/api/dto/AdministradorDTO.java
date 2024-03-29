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
    private Long Id;
    private String dataNascimento;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private Long loginId;
    public static AdministradorDTO create(Administrador administrador){
        ModelMapper modelMapper = new ModelMapper();
        AdministradorDTO dto = modelMapper.map(administrador, AdministradorDTO.class);
        dto.loginId = administrador.getLogin().getId();
        return dto;
    }
}

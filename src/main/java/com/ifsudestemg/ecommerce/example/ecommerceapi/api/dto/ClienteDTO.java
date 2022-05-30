package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private Date dataNascimento;
    private String cpf;

    private String nome;
    private Integer telefone;
    private String email;

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        dto.nome = cliente.getNome();
        dto.telefone = cliente.getTelefone();
        dto.email = cliente.getEmail();
        return dto;
    }

}

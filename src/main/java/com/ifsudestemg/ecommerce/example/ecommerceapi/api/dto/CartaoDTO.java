package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CarrinhoCompras;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {
    private Long Id;
    private String nomeTitular;
    private Integer numCartao;
    private Date validade;
    private String cvv;

    private String dataNascimento;
    private String cpf;

    private Long idCliente;
    private String nome;
    private Integer telefone;
    private String email;

    public static CartaoDTO create(Cartao cartao) {
        ModelMapper modelMapper = new ModelMapper();
        CartaoDTO dto = modelMapper.map(cartao, CartaoDTO.class);
        dto.dataNascimento = cartao.getCliente().getDataNascimento();
        dto.cpf = cartao.getCliente().getCpf();
        dto.idCliente = cartao.getCliente().getId();
        dto.nome = cartao.getCliente().getNome();
        dto.telefone = cartao.getCliente().getTelefone();
        dto.email = cartao.getCliente().getEmail();
        return dto;
    }
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {
    private Long Id;
    private String nomeTitular;
    private String numCartao;
    private String validade;
    private String cvv;


    public static CartaoDTO create(Cartao cartao) {
        ModelMapper modelMapper = new ModelMapper();
        CartaoDTO dto = modelMapper.map(cartao, CartaoDTO.class);
        return dto;
    }
}

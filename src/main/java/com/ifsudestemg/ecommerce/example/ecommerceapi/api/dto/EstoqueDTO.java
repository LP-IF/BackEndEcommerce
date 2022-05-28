package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Administrador;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Estoque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EstoqueDTO {
    private Long id;
    private Integer quantidadeEstoque;
    private Integer estoqueMinimo;
    private Integer estoqueMaximo;
    private Integer pontoRessuprimento;
    private String tamanho;
    private String cor;

    public static EstoqueDTO create(Estoque estoque){
        ModelMapper modelMapper = new ModelMapper();
        EstoqueDTO dto = modelMapper.map(estoque, EstoqueDTO.class);
        return dto;
    }

}

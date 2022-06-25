package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoVista;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagamentoVistaDTO {
    private Double desconto;
    private Double preco;
    private Long id;

    public static PagamentoVistaDTO create(PagamentoVista pagamentoVista) {
        ModelMapper modelMapper = new ModelMapper();
        PagamentoVistaDTO dto = modelMapper.map(pagamentoVista, PagamentoVistaDTO.class);
        return dto;
    }
}
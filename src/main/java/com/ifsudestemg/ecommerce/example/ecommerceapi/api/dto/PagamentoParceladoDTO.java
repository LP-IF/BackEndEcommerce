package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoParcelado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagamentoParceladoDTO {
    private Integer parcelas;
    private Double juros;
    private Double preco;
    private Long id;

    public static PagamentoParceladoDTO create(PagamentoParcelado pagamentoParcelado) {
        ModelMapper modelMapper = new ModelMapper();
        PagamentoParceladoDTO dto = modelMapper.map(pagamentoParcelado, PagamentoParceladoDTO.class);
        return dto;
    }
}
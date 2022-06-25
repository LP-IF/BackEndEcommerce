package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemVendaDTO {
    private Long Id;
    private Double precoUnit;
    private Double precoTotal;
    private Integer quant;

    private Long pagamentoId;

    public static ItemVendaDTO create(ItemVenda itemVenda){
        ModelMapper modelMapper = new ModelMapper();
        ItemVendaDTO dto = modelMapper.map(itemVenda, ItemVendaDTO.class);
        dto.pagamentoId = itemVenda.getPagamento().getId();
        return dto;
    }
}

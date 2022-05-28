package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemCompra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemCompraDTO {
    private Long Id;
    private Double custoUnit;
    private Integer quantidade;

    public static ItemCompraDTO create(ItemCompra itemCompra){
        ModelMapper modelMapper = new ModelMapper();
        ItemCompraDTO dto = modelMapper.map(itemCompra, ItemCompraDTO.class);
        return dto;
    }
}

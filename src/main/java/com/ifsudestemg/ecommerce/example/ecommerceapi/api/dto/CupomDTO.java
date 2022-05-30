package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cupom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {
    private Long Id;
    private Double porcentagem;

    public static CupomDTO create(Cupom cupom) {
        ModelMapper modelMapper = new ModelMapper();
        CupomDTO dto = modelMapper.map(cupom, CupomDTO.class);
        return dto;
    }
}

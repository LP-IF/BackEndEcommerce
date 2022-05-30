package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CarrinhoCompras;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoComprasDTO {
    private Long idCarrinhoCompras;
    private Integer qtdProduto;
    private Boolean freteGratis;
    private Float total;
    private Double precoFrete;

    private Long idCupom;
    private Double porcentagem;

    private Long idPagamento;
    private Double preco;

    public static CarrinhoComprasDTO create(CarrinhoCompras carrinhoCompras){
        ModelMapper modelMapper = new ModelMapper();
        CarrinhoComprasDTO dto = modelMapper.map(carrinhoCompras, CarrinhoComprasDTO.class);
        dto.idCupom = carrinhoCompras.getCupom().getId();
        dto.porcentagem = carrinhoCompras.getCupom().getPorcentagem();
        dto.idPagamento = carrinhoCompras.getPagamento().getId();
        dto.preco = carrinhoCompras.getPagamento().getPreco();
        return dto;
    }
}

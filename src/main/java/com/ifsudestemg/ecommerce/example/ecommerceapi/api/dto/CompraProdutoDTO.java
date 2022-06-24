package com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto;


import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cliente;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CompraProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraProdutoDTO {
    private Long Id;
    private Date dataEntrega;
    private Double custoTotal;

    private Long IdFornecedor;
    private String nome;
    private String telefone;
    private String email;

    public static CompraProdutoDTO create(CompraProduto compraProduto) {
        ModelMapper modelMapper = new ModelMapper();
        CompraProdutoDTO dto = modelMapper.map(compraProduto, CompraProdutoDTO.class);
        dto.IdFornecedor = compraProduto.getFornecedor().getId();
        dto.nome = compraProduto.getFornecedor().getNome();
        dto.telefone = compraProduto.getFornecedor().getTelefone();
        dto.email = compraProduto.getFornecedor().getEmail();
        return dto;
    }
}

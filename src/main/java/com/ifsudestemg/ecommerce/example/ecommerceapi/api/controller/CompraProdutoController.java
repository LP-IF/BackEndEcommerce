package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CompraProdutoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CompraProduto;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Fornecedor;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.CompraProdutoService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compraProdutos")
@RequiredArgsConstructor
public class CompraProdutoController {
    private final CompraProdutoService compraProdutoService;
    private final FornecedorService fornecedorService;

    @GetMapping()
    public ResponseEntity get() {
        List<CompraProduto> compraProdutos = compraProdutoService.getCompraProduto();
        return ResponseEntity.ok(compraProdutos.stream().map(CompraProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CompraProduto> compraProduto = compraProdutoService.getCompraProdutoById(id);
        if (!compraProduto.isPresent()) {
            return new ResponseEntity("Compra Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compraProduto.map(CompraProdutoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(CompraProdutoDTO dto) {
        try {
            CompraProduto compraProduto = converter(dto);
            Fornecedor fornecedor = fornecedorService.salvar(compraProduto.getFornecedor());
            compraProduto.setFornecedor(fornecedor);
            compraProduto = compraProdutoService.salvar(compraProduto);
            return new ResponseEntity(compraProduto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CompraProdutoDTO dto) {
        if (!compraProdutoService.getCompraProdutoById(id).isPresent()) {
            return new ResponseEntity("CompraProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            CompraProduto compraProduto = converter(dto);
            compraProduto.setId(id);
            Fornecedor fornecedor = fornecedorService.salvar(compraProduto.getFornecedor());
            compraProduto.setFornecedor(fornecedor);
            compraProdutoService.salvar(compraProduto);
            return ResponseEntity.ok(compraProduto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<CompraProduto> compraProduto = compraProdutoService.getCompraProdutoById(id);
        if (!compraProduto.isPresent()) {
            return new ResponseEntity("CompraProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            compraProdutoService.excluir(compraProduto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CompraProduto converter(CompraProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        CompraProduto compraProduto = modelMapper.map(dto, CompraProduto.class);
        if (dto.getIdFornecedor() != null) {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(dto.getIdFornecedor());
            if (!fornecedor.isPresent()) {
                compraProduto.setFornecedor(null);
            } else {
                compraProduto.setFornecedor(fornecedor.get());
            }
        }
        return compraProduto;
    }
}

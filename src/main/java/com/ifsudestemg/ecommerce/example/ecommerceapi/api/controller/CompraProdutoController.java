package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CompraProdutoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CompraProduto;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.CompraProdutoService;
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
    private final CompraProdutoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<CompraProduto> compraProdutos = service.getCompraProduto();
        return ResponseEntity.ok(compraProdutos.stream().map(CompraProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CompraProduto> compraProduto = service.getCompraProdutoById(id);
        if (!compraProduto.isPresent()) {
            return new ResponseEntity("Compra Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compraProduto.map(CompraProdutoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(CompraProdutoDTO dto) {
        try {
            CompraProduto compraProduto = converter(dto);
            compraProduto = service.salvar(compraProduto);
            return new ResponseEntity(compraProduto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CompraProdutoDTO dto) {
        if (!service.getCompraProdutoById(id).isPresent()) {
            return new ResponseEntity("CompraProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            CompraProduto compraProduto = converter(dto);
            compraProduto.setId(id);
            service.salvar(compraProduto);
            return ResponseEntity.ok(compraProduto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<CompraProduto> compraProduto = service.getCompraProdutoById(id);
        if (!compraProduto.isPresent()) {
            return new ResponseEntity("CompraProduto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compraProduto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CompraProduto converter(CompraProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        CompraProduto compraProduto = modelMapper.map(dto, CompraProduto.class);
        return compraProduto;
    }
}

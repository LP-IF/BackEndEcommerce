package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CompraProdutoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CompraProduto;
import com.ifsudestemg.ecommerce.service.CompraProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

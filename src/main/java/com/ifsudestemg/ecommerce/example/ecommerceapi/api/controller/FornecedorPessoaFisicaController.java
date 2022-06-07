package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaFisicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import com.ifsudestemg.ecommerce.service.FornecedorPessoaFisicaService;
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
@RequestMapping("/api/v1/fornecedoresPessoaFisica")
@RequiredArgsConstructor
public class FornecedorPessoaFisicaController {
    private final FornecedorPessoaFisicaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<FornecedorPessoaFisica> fornecedorPessoaFisicas = service.getFornecedorPessoaFisica();
        return ResponseEntity.ok(fornecedorPessoaFisicas.stream().map(FornecedorPessoaFisicaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FornecedorPessoaFisica> fornecedorPessoaFisica = service.getFornecedorPessoaFisicaById(id);
        if (!fornecedorPessoaFisica.isPresent()) {
            return new ResponseEntity("Fornecedor Pessoa Fisica não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedorPessoaFisica.map(FornecedorPessoaFisicaDTO::create));
    }
}

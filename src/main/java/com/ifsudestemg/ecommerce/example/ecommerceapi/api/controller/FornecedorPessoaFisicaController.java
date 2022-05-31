package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaFisicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import com.ifsudestemg.ecommerce.service.FornecedorPessoaFisicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

}

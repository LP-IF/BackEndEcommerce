package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaJuridicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaJuridica;
import com.ifsudestemg.ecommerce.service.FornecedorPessoaJuridicaService;
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
@RequestMapping("/api/v1/fornecedoresPessoaJuridica")
@RequiredArgsConstructor
public class FornecedorPessoaJuridicaController {
    private final FornecedorPessoaJuridicaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<FornecedorPessoaJuridica> fornecedorPessoaJuridicas = service.getFornecedorPessoaJuridica();
        return ResponseEntity.ok(fornecedorPessoaJuridicas.stream().map(FornecedorPessoaJuridicaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FornecedorPessoaJuridica> fornecedorPessoaJuridica = service.getFornecedorPessoaJuridicaById(id);
        if (!fornecedorPessoaJuridica.isPresent()) {
            return new ResponseEntity("Fornecedor Pessoa Juridica não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedorPessoaJuridica.map(FornecedorPessoaJuridicaDTO::create));
    }
}

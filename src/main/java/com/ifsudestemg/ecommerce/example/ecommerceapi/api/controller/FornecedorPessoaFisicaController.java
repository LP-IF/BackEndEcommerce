package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaFisicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.FornecedorPessoaFisicaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity post(FornecedorPessoaFisicaDTO dto) {
        try {
            FornecedorPessoaFisica fornecedorPessoaFisica = converter(dto);
            fornecedorPessoaFisica = service.salvar(fornecedorPessoaFisica);
            return new ResponseEntity(fornecedorPessoaFisica, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FornecedorPessoaFisicaDTO dto) {
        if (!service.getFornecedorPessoaFisicaById(id).isPresent()) {
            return new ResponseEntity("FornecedorPessoaFisica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FornecedorPessoaFisica fornecedorPessoaFisica = converter(dto);
            fornecedorPessoaFisica.setId(id);
            service.salvar(fornecedorPessoaFisica);
            return ResponseEntity.ok(fornecedorPessoaFisica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FornecedorPessoaFisica> fornecedorPessoaFisica = service.getFornecedorPessoaFisicaById(id);
        if (!fornecedorPessoaFisica.isPresent()) {
            return new ResponseEntity("FornecedorPessoaFisica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedorPessoaFisica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FornecedorPessoaFisica converter(FornecedorPessoaFisicaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FornecedorPessoaFisica fornecedorPessoaFisica = modelMapper.map(dto, FornecedorPessoaFisica.class);
        return fornecedorPessoaFisica;
    }
}

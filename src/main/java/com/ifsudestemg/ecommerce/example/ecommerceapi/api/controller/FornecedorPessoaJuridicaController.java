package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaJuridicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaJuridica;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.FornecedorPessoaJuridicaService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fornecedoresPessoaJuridica")
@RequiredArgsConstructor
public class FornecedorPessoaJuridicaController {
    private final FornecedorPessoaJuridicaService service;
    private final LoginService loginService;

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

    @PostMapping()
    public ResponseEntity post(FornecedorPessoaJuridicaDTO dto) {
        try {
            FornecedorPessoaJuridica fornecedorPessoaJuridica = converter(dto);
            fornecedorPessoaJuridica = service.salvar(fornecedorPessoaJuridica);
            return new ResponseEntity(fornecedorPessoaJuridica, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FornecedorPessoaJuridicaDTO dto) {
        if (!service.getFornecedorPessoaJuridicaById(id).isPresent()) {
            return new ResponseEntity("FornecedorPessoaJuridica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FornecedorPessoaJuridica fornecedorPessoaJuridica = converter(dto);
            fornecedorPessoaJuridica.setId(id);
            service.salvar(fornecedorPessoaJuridica);
            return ResponseEntity.ok(fornecedorPessoaJuridica);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FornecedorPessoaJuridica> fornecedorPessoaJuridica = service.getFornecedorPessoaJuridicaById(id);
        if (!fornecedorPessoaJuridica.isPresent()) {
            return new ResponseEntity("FornecedorPessoaJuridica não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedorPessoaJuridica.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FornecedorPessoaJuridica converter(FornecedorPessoaJuridicaDTO dto) {
        FornecedorPessoaJuridica fornecedorPessoaJuridica = new FornecedorPessoaJuridica();
        fornecedorPessoaJuridica.setId(dto.getId());
        fornecedorPessoaJuridica.setNome(dto.getNome());
        fornecedorPessoaJuridica.setEmail(dto.getEmail());
        fornecedorPessoaJuridica.setTelefone(dto.getTelefone());
        fornecedorPessoaJuridica.setCnpj(dto.getCnpj());
        fornecedorPessoaJuridica.setNomeRepresentante(dto.getNomeRepresentante());
        fornecedorPessoaJuridica.setCpfRepresentante(dto.getCpfRepresentante());
        fornecedorPessoaJuridica.setEmailRepresentante(dto.getEmailRepresentante());
        fornecedorPessoaJuridica.setTelRepresentante(dto.getTelRepresentante());
        return fornecedorPessoaJuridica;
    }
}

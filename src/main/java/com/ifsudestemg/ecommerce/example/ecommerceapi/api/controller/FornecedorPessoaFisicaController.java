package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.FornecedorPessoaFisicaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.FornecedorPessoaFisica;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.FornecedorPessoaFisicaService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
    private final LoginService loginService;

    @GetMapping()
    @ApiOperation("Obter todos os fornecedores pessoa física")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor Pessoa Física encontrado")})
    public ResponseEntity get() {
        List<FornecedorPessoaFisica> fornecedorPessoaFisicas = service.getFornecedorPessoaFisica();
        return ResponseEntity.ok(fornecedorPessoaFisicas.stream().map(FornecedorPessoaFisicaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um fornecedor pessoa física")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor pessoa física encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor pessoa física não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Administrador") Long id) {
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
        FornecedorPessoaFisica fornecedorPessoaFisica = new FornecedorPessoaFisica();
        fornecedorPessoaFisica.setId(dto.getId());
        fornecedorPessoaFisica.setNome(dto.getNome());
        fornecedorPessoaFisica.setEmail(dto.getEmail());
        fornecedorPessoaFisica.setTelefone(dto.getTelefone());
        fornecedorPessoaFisica.setDataNascimento(dto.getDataNascimento());
        fornecedorPessoaFisica.setCpf(dto.getCpf());
        return fornecedorPessoaFisica;
    }
}

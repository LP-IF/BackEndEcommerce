package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.AdministradorDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Administrador;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.AdministradorService;
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
@RequestMapping("/api/v1/administradores")
@RequiredArgsConstructor
public class AdministradorController {
    private final AdministradorService service;
    private final LoginService loginService;

    @GetMapping()
    @ApiOperation("Obter todos os administradores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Administrador encontrado")})
    public ResponseEntity get() {
        List<Administrador> administradores = service.getAdministrador();
        return ResponseEntity.ok(administradores.stream().map(AdministradorDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Administrador encontrado"),
            @ApiResponse(code = 404, message = "Administrador não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Administrador") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(administrador.map(AdministradorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Obter detalhes de um administrador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Administrador criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar administrador")})
    public ResponseEntity post(AdministradorDTO dto) {
        try {
            Administrador administrador = converter(dto);
            administrador = service.salvar(administrador);
            return new ResponseEntity(administrador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Obter detalhes de um administrador")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Administrador alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar administrador")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do Administrador") Long id, AdministradorDTO dto) {
        if (!service.getAdministradorById(id).isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Administrador administrador = converter(dto);
            administrador.setId(id);
            service.salvar(administrador);
            return ResponseEntity.ok(administrador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Obter detalhes de um administrador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Administrador excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir administrador"),
            @ApiResponse(code = 404, message = "Administrador não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Administrador") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(administrador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Administrador converter(AdministradorDTO dto) {
        Administrador administrador = new Administrador();
        administrador.setId(dto.getId());
        administrador.setNome(dto.getNome());
        administrador.setEmail(dto.getEmail());
        administrador.setTelefone(dto.getTelefone());
        administrador.setDataNascimento(dto.getDataNascimento());
        administrador.setCpf(dto.getCpf());
        administrador.setLogin(loginService.getLoginById(dto.getLoginId()).get());
        return administrador;
    }
}

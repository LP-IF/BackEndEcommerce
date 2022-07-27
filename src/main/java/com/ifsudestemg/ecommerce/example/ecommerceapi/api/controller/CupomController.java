package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CupomDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cupom;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.CupomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cupons")
@RequiredArgsConstructor
public class CupomController {
    private final CupomService service;

    @GetMapping()
    @ApiOperation("Obter todos os cupons")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cupom encontrado")})
    public ResponseEntity get() {
        List<Cupom> cupons = service.getCupom();
        return ResponseEntity.ok(cupons.stream().map(CupomDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cupom")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cupom encontrado"),
            @ApiResponse(code = 404, message = "Cupom não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("id do Cupom") Long id) {
        Optional<Cupom> cupom = service.getCupomById(id);
        if (!cupom.isPresent()) {
            return new ResponseEntity("Cupom não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cupom.map(CupomDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um cupom")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cupom criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar cupom")})
    public ResponseEntity post(CupomDTO dto) {
        try {
            Cupom cupom = converter(dto);
            cupom = service.salvar(cupom);
            return new ResponseEntity(cupom, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um cupom")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Cupom alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar cupom")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("id do Cupom") Long id, CupomDTO dto) {
        if (!service.getCupomById(id).isPresent()) {
            return new ResponseEntity("Cupom não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cupom cupom = converter(dto);
            cupom.setId(id);
            service.salvar(cupom);
            return ResponseEntity.ok(cupom);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um cupom")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cupom excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir cupom"),
            @ApiResponse(code = 404, message = "Cupom não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do cupom") Long id) {
        Optional<Cupom> cupom = service.getCupomById(id);
        if (!cupom.isPresent()) {
            return new ResponseEntity("Cupom não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cupom.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cupom converter(CupomDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cupom cupom = modelMapper.map(dto, Cupom.class);
        return cupom;
    }
}
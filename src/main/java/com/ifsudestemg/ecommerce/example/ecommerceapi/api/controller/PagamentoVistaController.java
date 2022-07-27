package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.PagamentoVistaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoVista;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.PagamentoVistaService;
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
@RequestMapping("/api/v1/pagamentosVista")
@RequiredArgsConstructor
public class PagamentoVistaController {
    private final PagamentoVistaService service;

    @GetMapping()
    @ApiOperation("Obter todos os pagamento a vistaes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pagamento a vista encontrado")})
    public ResponseEntity get() {
        List<PagamentoVista> pagamentoVistas = service.getPagamentoVista();
        return ResponseEntity.ok(pagamentoVistas.stream().map(PagamentoVistaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um pagamento a vista")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pagamento a vista encontrado"),
            @ApiResponse(code = 404, message = "Pagamento a vista não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Pagamento a vista") Long id) {
        Optional<PagamentoVista> pagamentoVista = service.getPagamentoVistaById(id);
        if (!pagamentoVista.isPresent()) {
            return new ResponseEntity("Pagamento à Vista não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pagamentoVista.map(PagamentoVistaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um pagamento a vista")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pagamento a vista criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar pagamento a vista")})
    public ResponseEntity post(PagamentoVistaDTO dto) {
        try {
            PagamentoVista pagamentoVista = converter(dto);
            pagamentoVista = service.salvar(pagamentoVista);
            return new ResponseEntity(pagamentoVista, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um pagamento a vista")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Pagamento a vista alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar pagamento a vista")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do Pagamento a vista") Long id, PagamentoVistaDTO dto) {
        if (!service.getPagamentoVistaById(id).isPresent()) {
            return new ResponseEntity("PagamentoVista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PagamentoVista pagamentoVista = converter(dto);
            pagamentoVista.setId(id);
            service.salvar(pagamentoVista);
            return ResponseEntity.ok(pagamentoVista);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um pagamento a vista")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pagamento a vista excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir pagamento a vista"),
            @ApiResponse(code = 404, message = "Pagamento a vista não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Pagamento a vista") Long id) {
        Optional<PagamentoVista> pagamentoVista = service.getPagamentoVistaById(id);
        if (!pagamentoVista.isPresent()) {
            return new ResponseEntity("PagamentoVista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pagamentoVista.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PagamentoVista converter(PagamentoVistaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PagamentoVista pagamentoVista = modelMapper.map(dto, PagamentoVista.class);
        return pagamentoVista;
    }
}

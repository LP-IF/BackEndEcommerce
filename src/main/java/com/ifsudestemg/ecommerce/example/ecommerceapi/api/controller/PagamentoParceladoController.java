package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.PagamentoParceladoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoParcelado;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.PagamentoParceladoService;
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
@RequestMapping("/api/v1/pagamentosParcelados")
@RequiredArgsConstructor
public class PagamentoParceladoController {
    private final PagamentoParceladoService service;

    @GetMapping()
    @ApiOperation("Obter todos os pagamento parceladoes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pagamento parcelado encontrado")})
    public ResponseEntity get() {
        List<PagamentoParcelado> pagamentoParcelados = service.getPagamentoParcelado();
        return ResponseEntity.ok(pagamentoParcelados.stream().map(PagamentoParceladoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um pagamento parcelado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pagamento parcelado encontrado"),
            @ApiResponse(code = 404, message = "Pagamento parcelado não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Pagamento parcelado") Long id) {
        Optional<PagamentoParcelado> pagamentoParcelado = service.getPagamentoParceladoById(id);
        if (!pagamentoParcelado.isPresent()) {
            return new ResponseEntity("Pagamento Parcelado não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pagamentoParcelado.map(PagamentoParceladoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um pagamento parcelado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pagamento parcelado criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar pagamento parcelado")})
    public ResponseEntity post(PagamentoParceladoDTO dto) {
        try {
            PagamentoParcelado pagamentoParcelado = converter(dto);
            pagamentoParcelado = service.salvar(pagamentoParcelado);
            return new ResponseEntity(pagamentoParcelado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um pagamento parcelado")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Pagamento parcelado alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar pagamento parcelado")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do Pagamento parcelado") Long id, PagamentoParceladoDTO dto) {
        if (!service.getPagamentoParceladoById(id).isPresent()) {
            return new ResponseEntity("PagamentoParcelado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PagamentoParcelado pagamentoParcelado = converter(dto);
            pagamentoParcelado.setId(id);
            service.salvar(pagamentoParcelado);
            return ResponseEntity.ok(pagamentoParcelado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um pagamento parcelado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pagamento parcelado excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir pagamento parcelado"),
            @ApiResponse(code = 404, message = "Pagamento parcelado não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Pagamento parcelado") Long id) {
        Optional<PagamentoParcelado> pagamentoParcelado = service.getPagamentoParceladoById(id);
        if (!pagamentoParcelado.isPresent()) {
            return new ResponseEntity("PagamentoParcelado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pagamentoParcelado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PagamentoParcelado converter(PagamentoParceladoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PagamentoParcelado pagamentoParcelado = modelMapper.map(dto, PagamentoParcelado.class);
        return pagamentoParcelado;
    }
}
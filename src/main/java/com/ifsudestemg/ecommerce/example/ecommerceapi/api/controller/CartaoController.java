package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CartaoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cliente;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.CartaoService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ClienteService;
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
@RequestMapping("/api/v1/cartoes")
@RequiredArgsConstructor
public class CartaoController {
    private final CartaoService cartaoService;
    
    private final ClienteService clienteService;

    @GetMapping()
    @ApiOperation("Obter todos os cartões")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cartão encontrado")})
    public ResponseEntity get() {
        List<Cartao> cartoes = cartaoService.getCartao();
        return ResponseEntity.ok(cartoes.stream().map(CartaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cartão")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cartão encontrado"),
            @ApiResponse(code = 404, message = "Cartão não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("id do cartão") Long id) {
        Optional<Cartao> cartao = cartaoService.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cartao.map(CartaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um cartão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cartão criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar cartão")})
    public ResponseEntity post(CartaoDTO dto) {
        try {
            Cartao cartao = converter(dto);
            Cliente cliente = clienteService.salvar(cartao.getCliente());
            cartao.setCliente(cliente);
            cartao = cartaoService.salvar(cartao);
            return new ResponseEntity(cartao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um cartão")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Cartão alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar cartão")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("id do cartão") Long id, CartaoDTO dto) {
        if (!cartaoService.getCartaoById(id).isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cartao cartao = converter(dto);
            cartao.setId(id);
            Cliente cliente = clienteService.salvar(cartao.getCliente());
            cartao.setCliente(cliente);
            cartaoService.salvar(cartao);
            return ResponseEntity.ok(cartao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um cartão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cartão excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir cartão"),
            @ApiResponse(code = 404, message = "Cartão não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Cartão") Long id) {
        Optional<Cartao> cartao = cartaoService.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            cartaoService.excluir(cartao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cartao converter(CartaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cartao cartao = modelMapper.map(dto, Cartao.class);
        if (dto.getClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getClienteId());
            if (!cliente.isPresent()) {
                cartao.setCliente(null);
            } else {
                cartao.setCliente(cliente.get());
            }
        }
        return cartao;
    }
}

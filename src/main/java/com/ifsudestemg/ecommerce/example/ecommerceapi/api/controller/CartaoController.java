package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CartaoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.CartaoService;
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
    private final CartaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Cartao> cartoes = service.getCartao();
        return ResponseEntity.ok(cartoes.stream().map(CartaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cartao> cartao = service.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cartao.map(CartaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(CartaoDTO dto) {
        try {
            Cartao cartao = converter(dto);
            cartao = service.salvar(cartao);
            return new ResponseEntity(cartao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CartaoDTO dto) {
        if (!service.getCartaoById(id).isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cartao cartao = converter(dto);
            cartao.setId(id);
            service.salvar(cartao);
            return ResponseEntity.ok(cartao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cartao> cartao = service.getCartaoById(id);
        if (!cartao.isPresent()) {
            return new ResponseEntity("Cartao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cartao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cartao converter(CartaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cartao cartao = modelMapper.map(dto, Cartao.class);
        return cartao;
    }
}

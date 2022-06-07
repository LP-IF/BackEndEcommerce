package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CartaoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cartao;
import com.ifsudestemg.ecommerce.service.CartaoService;
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
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.EstoqueDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Estoque;
import com.ifsudestemg.ecommerce.service.EstoqueService;
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
@RequestMapping("/api/v1/estoques")
@RequiredArgsConstructor
public class EstoqueController {
    private final EstoqueService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Estoque> estoques = service.getEstoque();
        return ResponseEntity.ok(estoques.stream().map(EstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estoque.map(EstoqueDTO::create));
    }

}
package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemCompraDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemCompra;
import com.ifsudestemg.ecommerce.service.ItemCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ItensCompra")
@RequiredArgsConstructor
public class ItemCompraController {
    private final ItemCompraService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ItemCompra> itemCompras = service.getItemCompra();
        return ResponseEntity.ok(itemCompras.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

}
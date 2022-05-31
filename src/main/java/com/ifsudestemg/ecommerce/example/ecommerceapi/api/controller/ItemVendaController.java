package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemVendaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemVenda;
import com.ifsudestemg.ecommerce.service.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ItensVenda")
@RequiredArgsConstructor
public class ItemVendaController {
    private final ItemVendaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ItemVenda> itemVendas = service.getItemVenda();
        return ResponseEntity.ok(itemVendas.stream().map(ItemVendaDTO::create).collect(Collectors.toList()));
    }

}

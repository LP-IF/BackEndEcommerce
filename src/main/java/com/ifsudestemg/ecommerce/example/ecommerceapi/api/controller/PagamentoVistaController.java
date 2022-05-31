package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.PagamentoVistaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoVista;
import com.ifsudestemg.ecommerce.service.PagamentoVistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pagamentosVista")
@RequiredArgsConstructor
public class PagamentoVistaController {
    private final PagamentoVistaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<PagamentoVista> pagamentoVistas = service.getPagamentoVista();
        return ResponseEntity.ok(pagamentoVistas.stream().map(PagamentoVistaDTO::create).collect(Collectors.toList()));
    }

}

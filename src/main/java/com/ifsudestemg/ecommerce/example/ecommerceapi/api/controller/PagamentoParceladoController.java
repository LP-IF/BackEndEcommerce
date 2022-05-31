package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.PagamentoParceladoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoParcelado;
import com.ifsudestemg.ecommerce.service.PagamentoParceladoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pagamentosParcelados")
@RequiredArgsConstructor
public class PagamentoParceladoController {
    private final PagamentoParceladoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<PagamentoParcelado> pagamentoParcelados = service.getPagamentoParcelado();
        return ResponseEntity.ok(pagamentoParcelados.stream().map(PagamentoParceladoDTO::create).collect(Collectors.toList()));
    }

}
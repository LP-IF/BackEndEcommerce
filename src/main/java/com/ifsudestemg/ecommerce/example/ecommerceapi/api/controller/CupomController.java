package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CupomDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cupom;
import com.ifsudestemg.ecommerce.service.CupomService;
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
@RequestMapping("/api/v1/cupons")
@RequiredArgsConstructor
public class CupomController {
    private final CupomService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Cupom> cupons = service.getCupom();
        return ResponseEntity.ok(cupons.stream().map(CupomDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cupom> cupom = service.getCupomById(id);
        if (!cupom.isPresent()) {
            return new ResponseEntity("Cupom não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cupom.map(CupomDTO::create));
    }
}
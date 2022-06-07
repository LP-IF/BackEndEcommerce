package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.AdministradorDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Administrador;
import com.ifsudestemg.ecommerce.service.AdministradorService;
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
@RequestMapping("/api/v1/administradores")
@RequiredArgsConstructor
public class AdministradorController {
    private final AdministradorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Administrador> administradores = service.getAdministrador();
        return ResponseEntity.ok(administradores.stream().map(AdministradorDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(administrador.map(AdministradorDTO::create));
    }
}

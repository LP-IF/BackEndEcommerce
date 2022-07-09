package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.AdministradorDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Administrador;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity post(AdministradorDTO dto) {
        try {
            Administrador administrador = converter(dto);
            administrador = service.salvar(administrador);
            return new ResponseEntity(administrador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, AdministradorDTO dto) {
        if (!service.getAdministradorById(id).isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Administrador administrador = converter(dto);
            administrador.setId(id);
            service.salvar(administrador);
            return ResponseEntity.ok(administrador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(administrador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Administrador converter(AdministradorDTO dto) {
        //ModelMapper modelMapper = new ModelMapper();
        //Administrador administrador = modelMapper.map(dto, Administrador.class);
        Administrador administrador = new Administrador();
        administrador.setId(dto.getId());
        administrador.setNome(dto.getNome());
        administrador.setEmail(dto.getEmail());
        administrador.setSenha(dto.getSenha());
        administrador.setTelefone(dto.getTelefone());
        administrador.setDataNascimento(dto.getDataNascimento());
        administrador.setCpf(dto.getCpf());
        return administrador;
    }
}

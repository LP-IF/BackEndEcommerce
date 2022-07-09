package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.PagamentoVistaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.PagamentoVista;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.PagamentoVistaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<PagamentoVista> pagamentoVista = service.getPagamentoVistaById(id);
        if (!pagamentoVista.isPresent()) {
            return new ResponseEntity("Pagamento à Vista não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pagamentoVista.map(PagamentoVistaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(PagamentoVistaDTO dto) {
        try {
            PagamentoVista pagamentoVista = converter(dto);
            pagamentoVista = service.salvar(pagamentoVista);
            return new ResponseEntity(pagamentoVista, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, PagamentoVistaDTO dto) {
        if (!service.getPagamentoVistaById(id).isPresent()) {
            return new ResponseEntity("PagamentoVista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            PagamentoVista pagamentoVista = converter(dto);
            pagamentoVista.setId(id);
            service.salvar(pagamentoVista);
            return ResponseEntity.ok(pagamentoVista);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<PagamentoVista> pagamentoVista = service.getPagamentoVistaById(id);
        if (!pagamentoVista.isPresent()) {
            return new ResponseEntity("PagamentoVista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pagamentoVista.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public PagamentoVista converter(PagamentoVistaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        PagamentoVista pagamentoVista = modelMapper.map(dto, PagamentoVista.class);
        return pagamentoVista;
    }
}

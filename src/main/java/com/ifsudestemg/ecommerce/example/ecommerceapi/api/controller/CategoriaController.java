package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CategoriaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Categoria;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Categoria> categorias = service.getCategoria();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            categoria = service.salvar(categoria);
            return new ResponseEntity(categoria, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CategoriaDTO dto) {
        if (!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            service.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
        public Categoria converter(CategoriaDTO dto) {
            ModelMapper modelMapper = new ModelMapper();
            Categoria categoria = modelMapper.map(dto, Categoria.class);
            return categoria;
        }
}

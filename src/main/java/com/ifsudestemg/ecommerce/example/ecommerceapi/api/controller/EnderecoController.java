package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.EnderecoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Endereco;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Endereco> enderecos = service.getEndereco();
        return ResponseEntity.ok(enderecos.stream().map(EnderecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Endereco> endereco = service.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(endereco.map(EnderecoDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(EnderecoDTO dto) {
        try {
            Endereco endereco = converter(dto);
            endereco = service.salvar(endereco);
            return new ResponseEntity(endereco, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, EnderecoDTO dto) {
        if (!service.getEnderecoById(id).isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Endereco endereco = converter(dto);
            endereco.setId(id);
            service.salvar(endereco);
            return ResponseEntity.ok(endereco);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Endereco> endereco = service.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(endereco.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Endereco converter(EnderecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        return endereco;
    }
}

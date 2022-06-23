package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemCompraDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemCompra;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.ItemCompraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ItemCompraDTO dto) {
        try {
            ItemCompra itemCompra = converter(dto);
            itemCompra = service.salvar(itemCompra);
            return new ResponseEntity(itemCompra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ItemCompraDTO dto) {
        if (!service.getItemCompraById(id).isPresent()) {
            return new ResponseEntity("ItemCompra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemCompra itemCompra = converter(dto);
            itemCompra.setId(id);
            service.salvar(itemCompra);
            return ResponseEntity.ok(itemCompra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("ItemCompra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemCompra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ItemCompra converter(ItemCompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ItemCompra itemCompra = modelMapper.map(dto, ItemCompra.class);
        return itemCompra;
    }
}
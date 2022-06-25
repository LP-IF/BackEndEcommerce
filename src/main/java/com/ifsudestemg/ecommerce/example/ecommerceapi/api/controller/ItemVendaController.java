package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemVendaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemVenda;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.service.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/itensVenda")
@RequiredArgsConstructor
public class ItemVendaController {
    private final ItemVendaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ItemVenda> itemVendas = service.getItemVenda();
        return ResponseEntity.ok(itemVendas.stream().map(ItemVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ItemVenda> itemVenda = service.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("Item Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemVenda.map(ItemVendaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ItemVendaDTO dto) {
        try {
            ItemVenda itemVenda = converter(dto);
            itemVenda = service.salvar(itemVenda);
            return new ResponseEntity(itemVenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ItemVendaDTO dto) {
        if (!service.getItemVendaById(id).isPresent()) {
            return new ResponseEntity("ItemVenda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemVenda itemVenda = converter(dto);
            itemVenda.setId(id);
            service.salvar(itemVenda);
            return ResponseEntity.ok(itemVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ItemVenda> itemVenda = service.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("ItemVenda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(itemVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ItemVenda converter(ItemVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ItemVenda itemVenda = modelMapper.map(dto, ItemVenda.class);
        return itemVenda;
    }
}

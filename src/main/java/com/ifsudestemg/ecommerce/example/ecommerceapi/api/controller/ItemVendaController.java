package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemVendaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ItemVendaService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.PagamentoService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ProdutoService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.VendaService;
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
    private final ItemVendaService itemVendaService;
    private final PagamentoService pagamentoService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;

    @GetMapping()
    public ResponseEntity get() {
        List<ItemVenda> itemVendas = itemVendaService.getItemVenda();
        return ResponseEntity.ok(itemVendas.stream().map(ItemVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ItemVenda> itemVenda = itemVendaService.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("Item Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemVenda.map(ItemVendaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ItemVendaDTO dto) {
        try {
            ItemVenda itemVenda = converter(dto);
            Pagamento pagamento = pagamentoService.salvar(itemVenda.getPagamento());
            itemVenda.setPagamento(pagamento);
            Produto produto = produtoService.salvar(itemVenda.getProduto());
            itemVenda.setProduto(produto);
            Venda venda = vendaService.salvar(itemVenda.getVenda());
            itemVenda.setVenda(venda);
            itemVenda = itemVendaService.salvar(itemVenda);
            return new ResponseEntity(itemVenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ItemVendaDTO dto) {
        if (!itemVendaService.getItemVendaById(id).isPresent()) {
            return new ResponseEntity("ItemVenda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemVenda itemVenda = converter(dto);
            itemVenda.setId(id);
            Pagamento pagamento = pagamentoService.salvar(itemVenda.getPagamento());
            itemVenda.setPagamento(pagamento);
            Produto produto = produtoService.salvar(itemVenda.getProduto());
            itemVenda.setProduto(produto);
            Venda venda = vendaService.salvar(itemVenda.getVenda());
            itemVenda.setVenda(venda);
            itemVendaService.salvar(itemVenda);
            return ResponseEntity.ok(itemVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ItemVenda> itemVenda = itemVendaService.getItemVendaById(id);
        if (!itemVenda.isPresent()) {
            return new ResponseEntity("ItemVenda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            itemVendaService.excluir(itemVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ItemVenda converter(ItemVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ItemVenda itemVenda = modelMapper.map(dto, ItemVenda.class);
        if (dto.getPagamentoId() != null) {
            Optional<Pagamento> pagamento = pagamentoService.getPagamentoById(dto.getPagamentoId());
            if (!pagamento.isPresent()) {
                itemVenda.setPagamento(null);
            } else {
                itemVenda.setPagamento(pagamento.get());
            }
        }
        if (dto.getProdutoId() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getProdutoId());
            if (!produto.isPresent()) {
                itemVenda.setProduto(null);
            } else {
                itemVenda.setProduto(produto.get());
            }
        }
        if (dto.getVendaId() != null) {
            Optional<Venda> venda = vendaService.getVendaById(dto.getVendaId());
            if (!venda.isPresent()) {
                itemVenda.setVenda(null);
            } else {
                itemVenda.setVenda(venda.get());
            }
        }
        return itemVenda;
    }
}

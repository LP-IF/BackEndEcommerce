package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ItemCompraDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.ItemCompra;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Produto;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ItemCompraService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/itensCompra")
@RequiredArgsConstructor
public class ItemCompraController {
    private final ItemCompraService service;
    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Obter todos os Itens compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item compra encontrado")})
    public ResponseEntity get() {
        List<ItemCompra> itemCompras = service.getItemCompra();
        return ResponseEntity.ok(itemCompras.stream().map(ItemCompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um item venda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Item compra encontrado"),
            @ApiResponse(code = 404, message = "Item compra não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do item compra") Long id) {
        Optional<ItemCompra> itemCompra = service.getItemCompraById(id);
        if (!itemCompra.isPresent()) {
            return new ResponseEntity("Item Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(itemCompra.map(ItemCompraDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um item compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item compra criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar item compra")})
    public ResponseEntity post(ItemCompraDTO dto) {
        try {
            ItemCompra itemCompra = converter(dto);
            Produto produto = produtoService.salvar(itemCompra.getProduto());
            itemCompra.setProduto(produto);
            itemCompra = service.salvar(itemCompra);
            return new ResponseEntity(itemCompra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um item compra")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Item compra alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar item compra")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, ItemCompraDTO dto) {
        if (!service.getItemCompraById(id).isPresent()) {
            return new ResponseEntity("ItemCompra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            ItemCompra itemCompra = converter(dto);
            itemCompra.setId(id);
            Produto produto = produtoService.salvar(itemCompra.getProduto());
            itemCompra.setProduto(produto);
            service.salvar(itemCompra);
            return ResponseEntity.ok(itemCompra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um item compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Item compra excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir item compra"),
            @ApiResponse(code = 404, message = "Item compra não encontrado")})
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
        if (dto.getProdutoId() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getProdutoId());
            if (!produto.isPresent()) {
                itemCompra.setProduto(null);
            } else {
                itemCompra.setProduto(produto.get());
            }
        }
        return itemCompra;
    }
}
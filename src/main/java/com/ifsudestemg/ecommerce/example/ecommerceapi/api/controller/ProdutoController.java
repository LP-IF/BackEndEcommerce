package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.ProdutoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Estoque;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Produto;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.EstoqueService;
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
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;

    @GetMapping()
    @ApiOperation("Obter todos os produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado")})
    public ResponseEntity get() {
        List<Produto> produtos = produtoService.getProduto();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Produto") Long id) {
        Optional<Produto> produto = produtoService.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar produto")})
    public ResponseEntity post(ProdutoDTO dto) {
        try {
            Produto produto = converter(dto);
            Estoque estoque = estoqueService.salvar(produto.getEstoque());
            produto.setEstoque(estoque);
            produto = produtoService.salvar(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um produto")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Produto alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar produto")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do Produto") Long id, ProdutoDTO dto) {
        if (!produtoService.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            Estoque estoque = estoqueService.salvar(produto.getEstoque());
            produto.setEstoque(estoque);
            produtoService.salvar(produto);
            return ResponseEntity.ok(produto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir produto"),
            @ApiResponse(code = 404, message = "Produto não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Produto") Long id) {
        Optional<Produto> produto = produtoService.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            produtoService.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        if (dto.getEstoqueId() != null) {
            Optional<Estoque> estoque = estoqueService.getEstoqueById(dto.getEstoqueId());
            if (!estoque.isPresent()) {
                produto.setEstoque(null);
            } else {
                produto.setEstoque(estoque.get());
            }
        }
        return produto;
    }
}

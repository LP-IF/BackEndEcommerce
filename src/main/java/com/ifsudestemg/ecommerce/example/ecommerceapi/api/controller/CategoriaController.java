package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CategoriaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Categoria;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Produto;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Obter todas as categorias")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada")})
    public ResponseEntity get() {
        List<Categoria> categorias = categoriaService.getCategoria();
        return ResponseEntity.ok(categorias.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada"),
            @ApiResponse(code = 404, message = "Categoria não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("id da categoria") Long id) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma categoria")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar categoria")})
    public ResponseEntity post(CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            Produto produto = produtoService.salvar(categoria.getProduto());
            categoria.setProduto(produto);
            categoria = categoriaService.salvar(categoria);
            return new ResponseEntity(categoria, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar uma categoria")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Categoria alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar categoria")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("id da categoria") Long id, CategoriaDTO dto) {
        if (!categoriaService.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            Produto produto = produtoService.salvar(categoria.getProduto());
            categoria.setProduto(produto);
            categoriaService.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar uma categoria")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Categoria excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir categoria"),
            @ApiResponse(code = 404, message = "Categoria não encontrada")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("id da Categoria") Long id) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            categoriaService.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
        public Categoria converter(CategoriaDTO dto) {
            ModelMapper modelMapper = new ModelMapper();
            Categoria categoria = modelMapper.map(dto, Categoria.class);
            if (dto.getProdutoId() != null) {
                Optional<Produto> produto = produtoService.getProdutoById(dto.getProdutoId());
                if (!produto.isPresent()) {
                    categoria.setProduto(null);
                } else {
                    categoria.setProduto(produto.get());
                }
            }
            return categoria;
        }
}

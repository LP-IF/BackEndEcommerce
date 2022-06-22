package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.ProdutoRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    private ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> getProduto() {
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto) {
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {
        if (produto.getNomeProduto() == null || produto.getNomeProduto().trim().equals("")) {
            throw new RegraNegocioException("Nome do Produto inválido");
        }
        if (produto.getEspecificacoes() == null || produto.getEspecificacoes().trim().equals("")) {
            throw new RegraNegocioException("Especificações inválidas");
        }
        if (produto.getPreco() == null || produto.getPreco() == 0) {
            throw new RegraNegocioException("Preço inválido");
        }
        if (produto.getEstoque() == null) {
            throw new RegraNegocioException("Estoque inválido");
        }
    }
}
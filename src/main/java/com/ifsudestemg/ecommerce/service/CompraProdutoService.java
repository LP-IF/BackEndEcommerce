package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.CompraProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraProdutoService {

    private CompraProdutoRepository repository;

    public CompraProdutoService(CompraProdutoRepository repository) {
        this.repository = repository;
    }

    public List<CompraProduto> getCompraProduto() {
        return repository.findAll();
    }

    public Optional<CompraProduto> getCompraProdutoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public CompraProduto salvar(CompraProduto compraproduto) {
        validar(compraproduto);
        return repository.save(compraproduto);
    }

    @Transactional
    public void excluir(CompraProduto compraproduto) {
        Objects.requireNonNull(compraproduto.getId());
        repository.delete(compraproduto);
    }

    public void validar(CompraProduto compraproduto) {
        if (compraproduto.getId() == null || compraproduto.getId() == 0) {
            throw new RegraNegocioException("Administrador inválido");
        }
        if (compraproduto.getDataEntrega() == null || compraproduto.getDataEntrega().equals("")) {
            throw new RegraNegocioException("Data de entrega inválida");
        }
    }
}

package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.CompraProduto;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.CompraProdutoRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
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
    public CompraProduto salvar(CompraProduto compraProduto) {
        validar(compraProduto);
        return repository.save(compraProduto);
    }

    @Transactional
    public void excluir(CompraProduto compraProduto) {
        Objects.requireNonNull(compraProduto.getId());
        repository.delete(compraProduto);
    }

    public void validar(CompraProduto compraProduto) {
        if (compraProduto.getId() == null || compraProduto.getId() == 0) {
            throw new RegraNegocioException("Compra produto inválido");
        }
        if (compraProduto.getDataEntrega() == null || compraProduto.getDataEntrega().equals("")) {
            throw new RegraNegocioException("Data de entrega inválida");
        }
        if (compraProduto.getCustoTotal() == null || compraProduto.getCustoTotal() == 0) {
            throw new RegraNegocioException("Custo total inválido");
        }
        if (compraProduto.getFornecedor() == null) {
            throw new RegraNegocioException("Fornecedor inválida");
        }

    }
}

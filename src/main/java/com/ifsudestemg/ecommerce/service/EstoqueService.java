package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.EstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository repository;

    public AdministradorService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public List<Estoque> getEstoque() {
        return repository.findAll();
    }

    public Optional<Estoque> getEstoqueById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Estoque salvar(Estoque estoque) {
        validar(estoque);
        return repository.save(estoque);
    }

    @Transactional
    public void excluir(Estoque estoque) {
        Objects.requireNonNull(estoque.getId());
        repository.delete(estoque);
    }

    public void validar(Estoque estoque) {
        if (estoque.getId() == null || estoque.getId() == 0) {
            throw new RegraNegocioException("Estoque inválido");
        }
        if (estoque.getTamanho() == null || estoque.getTamanho().trim().equals("")) {
            throw new RegraNegocioException("Tamanho inválido");
        }
    }
}

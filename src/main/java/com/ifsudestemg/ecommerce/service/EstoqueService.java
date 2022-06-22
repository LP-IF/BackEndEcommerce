package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Estoque;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.EstoqueRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
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
        if (estoque.getQuantidadeEstoque() == null) {
            throw new RegraNegocioException("Quantidade do estoque inválido");
        }
        if (estoque.getEstoqueMinimo() == null) {
            throw new RegraNegocioException("Estoque Mínimo inválido");
        }
        if (estoque.getEstoqueMaximo() == null) {
            throw new RegraNegocioException("Estoque Maximo inválido");
        }
        if (estoque.getPontoRessuprimento() == null) {
            throw new RegraNegocioException("Ponto de ressuprimento inválido");
        }
    }
}
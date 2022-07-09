package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.PagamentoParceladoRepository;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PagamentoParceladoService {

    private PagamentoParceladoRepository repository;

    public PagamentoParceladoService(PagamentoParceladoRepository repository) {
        this.repository = repository;
    }

    public List<PagamentoParcelado> getPagamentoParcelado() {
        return repository.findAll();
    }

    public Optional<PagamentoParcelado> getPagamentoParceladoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public PagamentoParcelado salvar(PagamentoParcelado pagamentoParcelado) {
        validar(pagamentoParcelado);
        return repository.save(pagamentoParcelado);
    }

    @Transactional
    public void excluir(PagamentoParcelado pagamentoParcelado) {
        Objects.requireNonNull(pagamentoParcelado.getId());
        repository.delete(pagamentoParcelado);
    }

    public void validar(PagamentoParcelado pagamentoParcelado) {
        if (pagamentoParcelado.getPreco() == null || pagamentoParcelado.getPreco() == 0) {
            throw new RegraNegocioException("Preço inválido");
        }
        if (pagamentoParcelado.getParcelas() == null || pagamentoParcelado.getParcelas() == 0) {
            throw new RegraNegocioException("Parcelas inválidas");
        }
        if (pagamentoParcelado.getJuros() == null || pagamentoParcelado.getJuros() == 0) {
            throw new RegraNegocioException("Juros inválidos");
        }
    }
}
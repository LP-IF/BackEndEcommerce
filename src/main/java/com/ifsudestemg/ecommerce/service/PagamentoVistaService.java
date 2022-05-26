package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.PagamentoVistaRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PagamentoVistaService {

    private PagamentoVistaRepository repository;

    public PagamentoVistaService(PagamentoVistaRepository repository) {
        this.repository = repository;
    }

    public List<PagamentoVista> getPagamentoVista() {
        return repository.findAll();
    }

    public Optional<PagamentoVista> getPagamentoVistaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public PagamentoVista salvar(PagamentoVista pagamentoVista) {
        validar(pagamentoVista);
        return repository.save(pagamentoVista);
    }

    @Transactional
    public void excluir(PagamentoVista pagamentoVista) {
        Objects.requireNonNull(pagamentoVista.getId());
        repository.delete(pagamentoVista);
    }

    public void validar(PagamentoVista pagamentoVista) {
        if (pagamentoVista.getId() == null || pagamentoVista.getId() == 0) {
            throw new RegraNegocioException("Pagamento à vista inválido");
        }
        if (pagamentoVista.getPreco() == null || pagamentoVista.getPreco() == 0) {
            throw new RegraNegocioException("Preço inválido");
        }
    }
}

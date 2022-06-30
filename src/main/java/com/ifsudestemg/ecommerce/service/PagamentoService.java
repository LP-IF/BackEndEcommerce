package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Pagamento;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PagamentoService {

    private PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public List<Pagamento> getPagamento() {
        return repository.findAll();
    }

    public Optional<Pagamento> getPagamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Pagamento salvar(Pagamento pagamento) {
        validar(pagamento);
        return repository.save(pagamento);
    }

    @Transactional
    public void excluir(Pagamento pagamento) {
        Objects.requireNonNull(pagamento.getId());
        repository.delete(pagamento);
    }

    public void validar(Pagamento pagamento) {

    }
}


package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.VendaRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {

    private VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public List<Venda> getVenda() {
        return repository.findAll();
    }

    public Optional<Venda> getVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Venda salvar(Venda venda) {
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda) {
        if (venda.getEstado() == null) {
            throw new RegraNegocioException("Estado da venda inválido");
        }
        if (venda.getDataVenda() == null) {
            throw new RegraNegocioException("Data da venda inválida");
        }
        if (venda.getDataEntrega() == null) {
            throw new RegraNegocioException("Data da entrega inválida");
        }
        if (venda.getNumeroPedido() == null || venda.getNumeroPedido() == 0) {
            throw new RegraNegocioException("Número do pedido inválido");
        }
    }
}
package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.ItemVendaRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemVendaService {

    private ItemVendaRepository repository;

    public ItemVendaService(ItemVendaRepository repository) {
        this.repository = repository;
    }

    public List<ItemVenda> getItemVenda() {
        return repository.findAll();
    }

    public Optional<ItemVenda> getItemVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItemVenda salvar(ItemVenda itemVenda) {
        validar(itemVenda);
        return repository.save(itemVenda);
    }

    @Transactional
    public void excluir(ItemVenda itemVenda) {
        Objects.requireNonNull(itemVenda.getId());
        repository.delete(itemVenda);
    }

    public void validar(ItemVenda itemVenda) {
        if (itemVenda.getId() == null || itemVenda.getId() == 0) {
            throw new RegraNegocioException("Item de Venda inválido");
        }
        if (itemVenda.getPrecoUnit() == null || itemVenda.getPrecoUnit() == 0) {
            throw new RegraNegocioException("Preço Unitário inválido");
        }
        if (itemVenda.getPrecoTotal() == null || itemVenda.getPrecoTotal() == 0) {
            throw new RegraNegocioException("Preço total inválido");
        }
        if (itemVenda.getQuant() == null || itemVenda.getQuant() == 0) {
            throw new RegraNegocioException("Quantidade inválida");
        }
    }
}
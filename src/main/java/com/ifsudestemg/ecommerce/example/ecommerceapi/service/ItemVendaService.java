package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.ItemVendaRepository;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
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
        if (itemVenda.getPrecoUnit() == null || itemVenda.getPrecoUnit() == 0) {
            throw new RegraNegocioException("Preço Unitário inválido");
        }
        if (itemVenda.getPrecoTotal() == null || itemVenda.getPrecoTotal() == 0) {
            throw new RegraNegocioException("Preço total inválido");
        }
        if (itemVenda.getQuant() == null || itemVenda.getQuant() == 0) {
            throw new RegraNegocioException("Quantidade inválida");
        }
        if (itemVenda.getPagamento() == null) {
            throw new RegraNegocioException("Pagamento inválida");
        }
        if (itemVenda.getProduto() == null) {
            throw new RegraNegocioException("Produto inválida");
        }
        if (itemVenda.getVenda() == null) {
            throw new RegraNegocioException("Venda inválida");
        }
    }
}
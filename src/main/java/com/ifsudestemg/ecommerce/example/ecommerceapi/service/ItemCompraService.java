package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.ItemCompraRepository;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemCompraService {

    private ItemCompraRepository repository;

    public ItemCompraService(ItemCompraRepository repository) {
        this.repository = repository;
    }

    public List<ItemCompra> getItemCompra() {
        return repository.findAll();
    }

    public Optional<ItemCompra> getItemCompraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ItemCompra salvar(ItemCompra itemCompra) {
        validar(itemCompra);
        return repository.save(itemCompra);
    }

    @Transactional
    public void excluir(ItemCompra itemCompra) {
        Objects.requireNonNull(itemCompra.getId());
        repository.delete(itemCompra);
    }

    public void validar(ItemCompra itemCompra) {
        if (itemCompra.getCustoUnit() == null || itemCompra.getCustoUnit() == 0) {
            throw new RegraNegocioException("Custo Unitário inválido");
        }
        if (itemCompra.getQuantidade() == null || itemCompra.getQuantidade() == 0) {
            throw new RegraNegocioException("Quantidade inválida");
        }
    }
}
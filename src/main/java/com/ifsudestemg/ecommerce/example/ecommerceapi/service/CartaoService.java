package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartaoService {

    private CartaoRepository repository;

    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public List<Cartao> getCartao() {
        return repository.findAll();
    }

    public Optional<Cartao> getCartaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cartao salvar(Cartao cartao) {
        validar(cartao);
        return repository.save(cartao);
    }

    @Transactional
    public void excluir(Cartao cartao) {
        Objects.requireNonNull(cartao.getId());
        repository.delete(cartao);
    }

    public void validar(Cartao cartao) {
        if (cartao.getCliente().getId() == null) {
            throw new RegraNegocioException("Cliente inválido");
        }
        if (cartao.getNumCartao() == null || cartao.getNumCartao().trim().equals("")){
            throw new RegraNegocioException("Número do cartão inválido");
        }
        if (cartao.getCvv() == null || cartao.getCvv().trim().equals("")) {
            throw new RegraNegocioException("CVV inválido");
        }
    }
}

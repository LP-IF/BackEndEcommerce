package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.CarrinhoComprasRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

    @Service
    public class CarrinhoComprasService {

        private CarrinhoComprasRepository repository;

        public CarrinhoComprasService(CarrinhoComprasRepository repository) {
            this.repository = repository;
        }

        public List<CarrinhoCompras> getcarrinhocompras() {
            return repository.findAll();
        }

        public Optional<CarrinhoCompras> getCarrinhoComprasById(Long id) {
            return repository.findById(id);
        }

        @Transactional
        public CarrinhoCompras salvar(CarrinhoCompras carrinhocompras) {
            validar(carrinhocompras);
            return repository.save(carrinhocompras);
        }

        @Transactional
        public void excluir(CarrinhoCompras carrinhocompras) {
            Objects.requireNonNull(carrinhocompras.getId());
            repository.delete(carrinhocompras);
        }

        public void validar(CarrinhoCompras carrinhocompras) {
            if (carrinhocompras.getId() == null || carrinhocompras.getId() == 0) {
                throw new RegraNegocioException("Carrinho inválido");
            }
            if (carrinhocompras.getQtdProduto() == null) {
                throw new RegraNegocioException("Quantidade de produtos inválida");
            }
            if (carrinhocompras.getCupom() == null || carrinhocompras.getCupom().equals("")) {
                throw new RegraNegocioException("Cupom inválido");
            }
        }
    }


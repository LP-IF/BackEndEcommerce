package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cupom;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.CupomRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CupomService {

    private CupomRepository repository;

    public CupomService(CupomRepository repository) {
        this.repository = repository;
    }

    public List<Cupom> getCupom() {
        return repository.findAll();
    }

    public Optional<Cupom> getCupomById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cupom salvar(Cupom cupom) {
        validar(cupom);
        return repository.save(cupom);
    }

    @Transactional
    public void excluir(Cupom cupom) {
        Objects.requireNonNull(cupom.getId());
        repository.delete(cupom);
    }

    public void validar(Cupom cupom) {
        if (cupom.getId() == null || cupom.getId() == 0) {
            throw new RegraNegocioException("Cupom inválido");
        }
        if (cupom.getPorcentagem() == null || cupom.getPorcentagem() == 0) {
            throw new RegraNegocioException("Porcentagem inválido");
        }
    }
}

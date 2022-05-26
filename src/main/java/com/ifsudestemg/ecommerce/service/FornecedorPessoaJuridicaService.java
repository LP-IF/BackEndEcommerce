package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.FornecedorPessoaJuridicaRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorPessoaJuridicaService {

    private FornecedorPessoaJuridicaRepository repository;

    public FornecedorPessoaJuridicaService(FornecedorPessoaJuridicaRepository repository) {
        this.repository = repository;
    }

    public List<FornecedorPessoaJuridica> getFornecedorPessoaJuridica() {
        return repository.findAll();
    }

    public Optional<FornecedorPessoaJuridica> getFornecedorPessoaJuridicaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FornecedorPessoaJuridica salvar(FornecedorPessoaJuridica fornecedorPessoaJuridica) {
        validar(fornecedorPessoaJuridica);
        return repository.save(fornecedorPessoaJuridica);
    }

    @Transactional
    public void excluir(FornecedorPessoaJuridica fornecedorPessoaJuridica) {
        Objects.requireNonNull(fornecedorPessoaJuridica.getId());
        repository.delete(fornecedorPessoaJuridica);
    }

    public void validar(FornecedorPessoaJuridica fornecedorPessoaJuridica) {
        if (fornecedorPessoaJuridica.getId() == null || fornecedorPessoaJuridica.getId() == 0) {
            throw new RegraNegocioException("Fornecedor Pessoa Jurídica inválido");
        }
        if (fornecedorPessoaJuridica.getCnpj() == null || fornecedorPessoaJuridica.getCnpj().trim().equals("")) {
            throw new RegraNegocioException("CNPJ inválido");
        }
    }
}

package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.FornecedorPessoaJuridicaRepository;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
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
        if (fornecedorPessoaJuridica.getCnpj() == null || fornecedorPessoaJuridica.getCnpj().trim().equals("")) {
            throw new RegraNegocioException("CNPJ inválido");
        }
        if (fornecedorPessoaJuridica.getNomeRepresentante() == null || fornecedorPessoaJuridica.getNomeRepresentante().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (fornecedorPessoaJuridica.getEmailRepresentante() == null || fornecedorPessoaJuridica.getEmailRepresentante().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }
}

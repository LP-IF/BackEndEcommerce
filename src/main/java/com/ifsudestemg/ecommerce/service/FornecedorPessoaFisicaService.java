package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.FornecedorPessoaFisicaRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorPessoaFisicaService {

    private FornecedorPessoaFisicaRepository repository;

    public FornecedorPessoaFisicaService(FornecedorPessoaFisicaRepository repository) {
        this.repository = repository;
    }

    public List<FornecedorPessoaFisica> getFornecedorPessoaFisica() {
        return repository.findAll();
    }

    public Optional<FornecedorPessoaFisica> getFornecedorPessoaFisicaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FornecedorPessoaFisica salvar(FornecedorPessoaFisica fornecedorPessoaFisica) {
        validar(fornecedorPessoaFisica);
        return repository.save(fornecedorPessoaFisica);
    }

    @Transactional
    public void excluir(FornecedorPessoaFisica fornecedorPessoaFisica) {
        Objects.requireNonNull(fornecedorPessoaFisica.getId());
        repository.delete(fornecedorPessoaFisica);
    }

    public void validar(FornecedorPessoaFisica fornecedorPessoaFisica) {
        if (fornecedorPessoaFisica.getId() == null || fornecedorPessoaFisica.getId() == 0) {
            throw new RegraNegocioException("Fornecedor Pessoa Física inválido");
        }
        if (fornecedorPessoaFisica.getNome() == null || fornecedorPessoaFisica.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (fornecedorPessoaFisica.getEmail() == null || fornecedorPessoaFisica.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (fornecedorPessoaFisica.getSenha() == null || fornecedorPessoaFisica.getSenha().trim().equals("")) {
            throw new RegraNegocioException("Senha inválida");
        }
        if (fornecedorPessoaFisica.getCpf() == null || fornecedorPessoaFisica.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
    }
}

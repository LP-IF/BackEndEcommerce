package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Fornecedor;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.FornecedorRepository;
import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    private FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<Fornecedor> getFornecedor() {
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedorPessoaFisica) {
        validar(fornecedorPessoaFisica);
        return repository.save(fornecedorPessoaFisica);
    }

    @Transactional
    public void excluir(Fornecedor fornecedorPessoaFisica) {
        Objects.requireNonNull(fornecedorPessoaFisica.getId());
        repository.delete(fornecedorPessoaFisica);
    }

    public void validar(Fornecedor fornecedorPessoaFisica) {
        if (fornecedorPessoaFisica.getNome() == null || fornecedorPessoaFisica.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (fornecedorPessoaFisica.getEmail() == null || fornecedorPessoaFisica.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (fornecedorPessoaFisica.getSenha() == null || fornecedorPessoaFisica.getSenha().trim().equals("")) {
            throw new RegraNegocioException("Senha inválida");
        }
    }
}

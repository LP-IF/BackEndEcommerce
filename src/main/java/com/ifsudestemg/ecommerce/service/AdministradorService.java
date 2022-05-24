package com.ifsudestemg.ecommerce.service;

import com.ifsudestemg.ecommerce.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.*;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.AdministradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdministradorService {

    private AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    public List<Administrador> getAdministrador() {
        return repository.findAll();
    }

    public Optional<Administrador> getAdministradorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Administrador salvar(Administrador administrador) {
        validar(administrador);
        return repository.save(administrador);
    }

    @Transactional
    public void excluir(Administrador administrador) {
        Objects.requireNonNull(administrador.getIdAdministrador());
        repository.delete(administrador);
    }

    public void validar(Administrador administrador) {
        if (administrador.getIdAdministrador() == null || administrador.getIdAdministrador() == 0) {
            throw new RegraNegocioException("Administrador inválido");
        }
        if (administrador.getDataNascimento() == null){
            throw new RegraNegocioException("Data de Nascimento inválida");
        }
        if (administrador.getCpf() == null || administrador.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
    }
}
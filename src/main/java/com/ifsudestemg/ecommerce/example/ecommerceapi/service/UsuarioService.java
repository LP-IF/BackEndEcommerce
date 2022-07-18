package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.SenhaInvalidaException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Usuario;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> getUsuario() {
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Usuario salvar(Usuario endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    @Transactional
    public void excluir(Usuario endereco) {
        Objects.requireNonNull(endereco.getId());
        repository.delete(endereco);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = usuario.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    public void validar(Usuario endereco) {

    }
}

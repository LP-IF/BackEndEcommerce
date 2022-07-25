package com.ifsudestemg.ecommerce.example.ecommerceapi.service;

import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.SenhaInvalidaException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Login;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LoginRepository repository;

    public Optional<Login> getLoginById(Long id) {
        return repository.findById(id);
    }
    @Transactional
    public Login salvar(Login login){
        return repository.save(login);
    }

    public UserDetails autenticar(Login login){
        UserDetails user = loadUserByUsername(login.getLogin());
        boolean senhasBatem = encoder.matches(login.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login login = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = login.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(login.getLogin())
                .password(login.getSenha())
                .roles(roles)
                .build();
    }
}

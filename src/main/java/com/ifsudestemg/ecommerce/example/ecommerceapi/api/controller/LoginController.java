package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.CredenciaisDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.TokenDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.SenhaInvalidaException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Login;
import com.ifsudestemg.ecommerce.example.ecommerceapi.security.JwtService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/logins")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Login salvar(@RequestBody Login login ){
        String senhaCriptografada = passwordEncoder.encode(login.getSenha());
        login.setSenha(senhaCriptografada);
        return loginService.salvar(login);
    }


    @PostMapping("/auth")
    @ApiOperation("Autenticação de login")
    @ApiResponses({
            @ApiResponse(code = 201, message = "login autenticado com sucesso"),
            @ApiResponse(code = 401, message = "Login não autorizado")})
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Login login = Login.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails loginAutenticado = loginService.autenticar(login);
            String token = jwtService.gerarToken(login);
            return new TokenDTO(login.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}

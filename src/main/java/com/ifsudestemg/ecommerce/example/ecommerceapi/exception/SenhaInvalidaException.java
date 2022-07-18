package com.ifsudestemg.ecommerce.example.ecommerceapi.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
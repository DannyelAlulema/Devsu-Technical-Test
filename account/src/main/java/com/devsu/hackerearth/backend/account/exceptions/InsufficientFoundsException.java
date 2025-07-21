package com.devsu.hackerearth.backend.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFoundsException extends RuntimeException {
    public InsufficientFoundsException() {
        super("Saldo insuficiente");
    }
}

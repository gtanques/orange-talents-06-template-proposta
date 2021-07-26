package com.orange.proposta.configuracoes.exceptions;

import org.springframework.http.HttpStatus;

public class ExceptionPersonalizada extends RuntimeException {

    private final HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExceptionPersonalizada(String mensagem, HttpStatus httpStatus)
    {
        super(mensagem);
        this.httpStatus = httpStatus;

    }

}

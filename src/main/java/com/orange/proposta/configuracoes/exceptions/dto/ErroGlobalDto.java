package com.orange.proposta.configuracoes.exceptions.dto;

public class ErroGlobalDto {

    private final String erro;

    public ErroGlobalDto(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

}

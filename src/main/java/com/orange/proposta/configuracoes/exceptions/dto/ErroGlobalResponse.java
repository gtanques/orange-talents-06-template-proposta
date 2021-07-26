package com.orange.proposta.configuracoes.exceptions.dto;

public class ErroGlobalResponse {

    private final String erro;

    public ErroGlobalResponse(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

}

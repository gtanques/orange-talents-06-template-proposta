package com.orange.proposta.configuracoes.exceptions.dto;

public class ErroDeFormularioDto {

    private final String campo;
    private final String erro;

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }

    public ErroDeFormularioDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

}

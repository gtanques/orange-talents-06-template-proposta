package com.orange.proposta.configuracoes;

public class ErroResponse {

    private final String campo;
    private final String erro;

    public String getCampo() {
        return campo;
    }

    public ErroResponse(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

}

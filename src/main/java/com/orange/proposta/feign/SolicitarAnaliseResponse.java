package com.orange.proposta.feign;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SolicitarAnaliseResponse {

    private final String resultadoSolicitacao;

    @JsonCreator
    public SolicitarAnaliseResponse(String resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getResultadoSolicitacao() { return resultadoSolicitacao; }

}

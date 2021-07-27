package com.orange.proposta.analises.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SolicitarAnaliseResponse {

    private final String resultadoSolicitacao;

    @JsonCreator
    public SolicitarAnaliseResponse(String resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getResultadoSolicitacao() { return resultadoSolicitacao; }

}

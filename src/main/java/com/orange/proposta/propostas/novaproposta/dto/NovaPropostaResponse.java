package com.orange.proposta.propostas.novaproposta.dto;

import com.orange.proposta.propostas.novaproposta.Proposta;

public class NovaPropostaResponse {

    private String id;

    public NovaPropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
    }

    @Deprecated
    private NovaPropostaResponse() {
    }

    public String getId() { return id; }

}

package com.orange.proposta.propostas;

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

package com.orange.proposta.propostas;

public class AcompanharPropostaResponse {

    private StatusFinanceiroEnum statusProposta;

    public AcompanharPropostaResponse(Proposta proposta) {
        this.statusProposta = proposta.getStatusFinanceiro();
    }

    public StatusFinanceiroEnum getStatusProposta() {
        return statusProposta;
    }

}

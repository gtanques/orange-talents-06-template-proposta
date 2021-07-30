package com.orange.proposta.propostas.acompanhapropostas.dto;

import com.orange.proposta.propostas.novaproposta.Proposta;
import com.orange.proposta.propostas.novaproposta.enumerador.StatusFinanceiro;

public class AcompanharPropostaResponse {

    private StatusFinanceiro statusProposta;

    public AcompanharPropostaResponse(Proposta proposta) {
        this.statusProposta = proposta.getStatusFinanceiro();
    }

    public StatusFinanceiro getStatusProposta() {
        return statusProposta;
    }

}

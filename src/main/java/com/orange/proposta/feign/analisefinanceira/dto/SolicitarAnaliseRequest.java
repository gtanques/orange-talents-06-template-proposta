package com.orange.proposta.feign.analisefinanceira.dto;

import com.orange.proposta.configuracoes.exceptions.annotation.cpfcnpj.CpfCnpjValid;
import com.orange.proposta.novaproposta.Proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SolicitarAnaliseRequest {

    @CpfCnpjValid
    @NotNull
    @NotBlank
    private String documento;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String idProposta;

    public SolicitarAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getCpfCnpj();
        this.nome = proposta.getNome();
        this.idProposta = String.valueOf(proposta.getId());
    }

    public String getDocumento() { return documento; }

    public String getNome() { return nome; }

    public String getIdProposta() { return idProposta; }

}

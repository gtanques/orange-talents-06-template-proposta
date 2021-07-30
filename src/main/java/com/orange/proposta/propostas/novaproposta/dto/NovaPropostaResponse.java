package com.orange.proposta.propostas.novaproposta.dto;

import com.orange.proposta.propostas.novaproposta.Proposta;
import com.orange.proposta.propostas.novaproposta.enumerador.StatusFinanceiro;

import java.math.BigDecimal;

public class NovaPropostaResponse {
    private final String cpfCnpj;
    private final String email;
    private final String nome;
    private final String endereco;
    private final BigDecimal salario;
    private final StatusFinanceiro statusFinanceiro;

    public NovaPropostaResponse(Proposta proposta) {
        this.cpfCnpj = proposta.getCpfCnpj();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.statusFinanceiro = proposta.getStatusFinanceiro();
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusFinanceiro getStatusFinanceiro() { return statusFinanceiro; }
    
}

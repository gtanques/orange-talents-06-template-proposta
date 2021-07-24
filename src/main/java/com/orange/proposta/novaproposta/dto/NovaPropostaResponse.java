package com.orange.proposta.novaproposta.dto;

import com.orange.proposta.novaproposta.Proposta;

import java.math.BigDecimal;

public class NovaPropostaResponse {
    private final String cpfCnpj;
    private final String email;
    private final String nome;
    private final String endereço;
    private final BigDecimal salario;

    public NovaPropostaResponse(Proposta proposta) {
        this.cpfCnpj = proposta.getCpfCnpj();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereço = proposta.getEndereço();
        this.salario = proposta.getSalario();
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

    public String getEndereço() {
        return endereço;
    }

    public BigDecimal getSalario() {
        return salario;
    }

}

package com.orange.proposta.novaproposta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.orange.proposta.configuracoes.exceptions.annotation.cpfcnpj.CpfCnpjValid;
import com.orange.proposta.novaproposta.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotNull
    @NotBlank
    @CpfCnpjValid
    private String cpfCnpj;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String endereco;

    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaPropostaRequest(String cpfCnpj, String email, String nome, String endereco, BigDecimal salario) {
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(this.cpfCnpj, this.email, this.nome, this.endereco, this.salario);
    }

}

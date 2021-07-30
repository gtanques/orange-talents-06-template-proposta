package com.orange.proposta.propostas.novaproposta.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.configuracoes.exceptions.annotation.cpfcnpj.CpfCnpjValid;
import com.orange.proposta.propostas.novaproposta.Proposta;
import com.orange.proposta.propostas.novaproposta.repository.PropostaRepository;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Optional;

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

    public Proposta toModel(PropostaRepository propostaRepository) {
        Optional<Proposta> existeProposta = propostaRepository.findByCpfCnpj(this.cpfCnpj);

        if (existeProposta.isPresent()) {
            throw new ExceptionPersonalizada("Já existe uma proposta cadastrada para esse CPF/CNPJ.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new Proposta(this.cpfCnpj, this.email, this.nome, this.endereco, this.salario);
    }

}

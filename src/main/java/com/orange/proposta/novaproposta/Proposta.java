package com.orange.proposta.novaproposta;

import com.orange.proposta.novaproposta.enumerador.StatusFinanceiro;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusFinanceiro statusFinanceiro;

    private String numeroCartao;

    @Deprecated
    private Proposta() {
    }

    public Proposta(@NotNull @NotBlank String cpfCnpj,
                    @Email @NotNull @NotBlank String email,
                    @NotNull @NotBlank String nome,
                    @NotNull @NotBlank String endereco,
                    @NotNull @PositiveOrZero BigDecimal salario) {
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() { return id; }

    public String getCpfCnpj() { return cpfCnpj; }

    public String getEmail() { return email; }

    public String getNome() { return nome; }

    public String getEndereco() { return endereco; }

    public BigDecimal getSalario() { return salario; }

    public StatusFinanceiro getStatusFinanceiro() { return statusFinanceiro; }

    public String getNumeroCartao() { return numeroCartao; }

    public void definirStatusFinanceiro(String status){
        if(status.equals("COM_RESTRICAO")){
            this.statusFinanceiro = StatusFinanceiro.NAO_ELEGIVEL;
        }else{
            this.statusFinanceiro = StatusFinanceiro.ELEGIVEL;
        }
    }

    public void definirNumeroCartao(String numeroCartao){
        this.numeroCartao = numeroCartao;
    }

}

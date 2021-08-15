package com.orange.proposta.propostas;

import com.orange.proposta.cartoes.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    private final String id = UUID.randomUUID().toString().replace("-","");

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
    private StatusFinanceiroEnum statusFinanceiro;

    @OneToOne
    private Cartao cartao;

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

    public String getId() { return id; }

    public String getCpfCnpj() { return cpfCnpj; }

    public String getNome() { return nome; }

    public StatusFinanceiroEnum getStatusFinanceiro() { return statusFinanceiro; }

    public void AdicionaStatusFinanceiroNaProposta(String status){
        if(status.equals("COM_RESTRICAO")){
            this.statusFinanceiro = StatusFinanceiroEnum.NAO_ELEGIVEL;
        }else{
            this.statusFinanceiro = StatusFinanceiroEnum.ELEGIVEL;
        }
    }

    public void adicionaCartaoNaProposta(Cartao cartao){
        this.cartao = cartao;
    }

}

package com.orange.proposta.novaproposta;

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
    private String endereço;

    @Column(nullable = false)
    private BigDecimal salario;


    @Deprecated
    private Proposta() {
    }

    public Proposta(@NotNull @NotBlank String cpfCnpj,
                    @Email @NotNull @NotBlank String email,
                    @NotNull @NotBlank String nome,
                    @NotNull @NotBlank String endereço,
                    @NotNull @PositiveOrZero BigDecimal salario) {
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereço = endereço;
        this.salario = salario;
    }

    public Long getId() {
        return id;
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

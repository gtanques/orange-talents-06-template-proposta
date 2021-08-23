package com.orange.proposta.carteiras;

import com.orange.proposta.cartoes.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCarteiraEnum tipoCarteira;

    @ManyToOne
    private Cartao cartao;

    @Column(nullable = false, updatable = false)
    private Instant criadoEm = Instant.now();

    @Deprecated
    private Carteira() {
    }

    public Carteira(@NotBlank @NotNull @Email String email, TipoCarteiraEnum tipoCarteira, Cartao cartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

}
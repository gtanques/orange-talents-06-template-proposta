package com.orange.proposta.biometrias;

import com.orange.proposta.cartoes.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_biometria")
public class Biometria {

    @Id
    private final String id = UUID.randomUUID().toString().replace("-","");

    @Column(nullable = false)
    private String fingerprint;

    @ManyToOne
    private Cartao cartao;

    @Column(updatable = false)
    private final Instant dataCriacao = Instant.now();

    @Deprecated
    private Biometria() {
    }

    public Biometria(@NotNull @NotBlank String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

}

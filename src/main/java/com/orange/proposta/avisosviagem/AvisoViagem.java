package com.orange.proposta.avisosviagem;

import com.orange.proposta.cartoes.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "tb_aviso_viagem")
public class AvisoViagem {

    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDate validoAte;

    @Column(nullable = false)
    private String ipRequest;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false, updatable = false)
    private final Instant dataCriacao = Instant.now();

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    private AvisoViagem() {
    }

    public AvisoViagem(@NotBlank @NotNull String destino,
                       @Future @NotNull LocalDate validoAte,
                       @NotEmpty String ipRequest,
                       @NotEmpty String userAgent,
                       @NotNull Cartao cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ipRequest = ipRequest;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

}

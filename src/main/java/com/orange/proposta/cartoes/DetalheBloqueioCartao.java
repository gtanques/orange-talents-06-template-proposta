package com.orange.proposta.cartoes;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_detalhe_bloqueio_cartao")
public class DetalheBloqueioCartao {

    @Id
    private final String id = UUID.randomUUID().toString().replace("-","");

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCartaoEnum statusCartaoEnum;

    @ManyToOne
    private Cartao cartao;

    @Column(nullable = false, updatable = false)
    private final Instant dataModificacao = Instant.now();

    @Column(nullable = false)
    private String ipCliente;

    @Column(nullable = false)
    private String userAgent;

    public DetalheBloqueioCartao(StatusCartaoEnum statusCartaoEnum, Cartao cartao, String ipCliente, String userAgent) {
        this.statusCartaoEnum = statusCartaoEnum;
        this.cartao = cartao;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    @Deprecated
    public DetalheBloqueioCartao() {
    }

}

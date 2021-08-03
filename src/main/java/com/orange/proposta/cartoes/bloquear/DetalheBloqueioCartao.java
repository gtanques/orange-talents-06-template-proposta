package com.orange.proposta.cartoes.bloquear;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloquear.enumeradores.StatusCartao;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_detalhe_status_cartao")
public class DetalheBloqueioCartao {

    @Id
    private final String id = UUID.randomUUID().toString().replace("-","");

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @ManyToOne
    private Cartao cartao;

    @Column(nullable = false, updatable = false)
    private final Instant dataModificacao = Instant.now();

    @Column(nullable = false)
    private String ipCliente;

    @Column(nullable = false)
    private String userAgent;

    public DetalheBloqueioCartao(StatusCartao statusCartao, Cartao cartao, String ipCliente, String userAgent) {
        this.statusCartao = statusCartao;
        this.cartao = cartao;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    @Deprecated
    public DetalheBloqueioCartao() {
    }

}

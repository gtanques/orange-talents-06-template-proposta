package com.orange.proposta.cartoes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    private final String id = UUID.randomUUID().toString().replace("-","");

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    @Column(updatable = false)
    private final Instant dataCriacao = Instant.now();

    public Cartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @Deprecated
    private Cartao() {
    }

}

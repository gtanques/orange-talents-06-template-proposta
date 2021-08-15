package com.orange.proposta.cartoes;

import com.orange.proposta.avisosviagem.AvisoViagem;
import com.orange.proposta.biometrias.Biometria;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "cartao", fetch = FetchType.EAGER)
    List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", fetch = FetchType.LAZY)
    List<AvisoViagem> avisosViagem = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusCartaoEnum statusCartaoEnum = StatusCartaoEnum.DESBLOQUEADO;

    public Cartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @Deprecated
    private Cartao() {
    }

    public StatusCartaoEnum getStatusCartao() { return statusCartaoEnum; }

    public String getNumeroCartao() { return numeroCartao; }

    public boolean verificaSeStatusFoiAlterado(StatusCartaoEnum statusCartaoEnum){
        if (statusCartaoEnum.equals(this.statusCartaoEnum)){
            return false;
        }

        this.statusCartaoEnum = statusCartaoEnum;
        return true;
    }

}

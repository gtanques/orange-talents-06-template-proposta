package com.orange.proposta.biometrias;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.orange.proposta.annotation.BiometriaBase64Valid;
import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.CartaoRepository;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaBiometriaResquest {

    @NotNull
    @NotBlank
    @BiometriaBase64Valid
    private final String fingerprint;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaBiometriaResquest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria toModel(CartaoRepository cartaoRepository, String numeroCartao){
        Cartao cartao = cartaoRepository.
                findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new ExceptionPersonalizada("Cartão: " + numeroCartao + " não encontrado!", HttpStatus.NOT_FOUND));
        return new Biometria(this.fingerprint, cartao);
    }

}

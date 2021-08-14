package com.orange.proposta.cartoes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import org.springframework.http.HttpStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class BloquearCartaoRequest {

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCartaoEnum statusCartaoEnum;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoRequest(StatusCartaoEnum statusCartaoEnum) {
        this.statusCartaoEnum = statusCartaoEnum;
    }

    public Cartao toModel(CartaoRepository cartaoRepository, String numeroCartao){
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new ExceptionPersonalizada("Cartão não encontrado.", HttpStatus.NOT_FOUND));

        boolean statusAlterado = cartao.verificaSeStatusFoiAlterado(statusCartaoEnum);

        if (!statusAlterado){
            throw new ExceptionPersonalizada("O cartão já está " + cartao.getStatusCartao(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return cartao;
    }

}

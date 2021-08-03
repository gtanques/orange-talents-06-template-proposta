package com.orange.proposta.cartoes.bloquear.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloquear.enumeradores.StatusCartao;
import com.orange.proposta.cartoes.repository.CartaoRepository;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import org.springframework.http.HttpStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class BloquearCartaoRequest {

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoRequest(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
    }

    public Cartao toModel(CartaoRepository cartaoRepository, String numeroCartao){
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new ExceptionPersonalizada("Cartão não encontrado.", HttpStatus.NOT_FOUND));

        boolean statusAlterado = cartao.verificaSeStatusFoiAlterado(statusCartao);

        if (!statusAlterado){
            throw new ExceptionPersonalizada("O cartão já está " + cartao.getStatusCartao(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return cartao;
    }

}

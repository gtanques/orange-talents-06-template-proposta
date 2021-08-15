package com.orange.proposta.avisosviagem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.CartaoRepository;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    @NotNull
    private final String destino;

    @Future
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private final LocalDate dataTermino;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoViagemRequest(String destino, LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public AvisoViagem toModel(HttpServletRequest httpRequest, CartaoRepository cartaoRepository, String numeroCartao){
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new ExceptionPersonalizada("Cartão não encontrado.", HttpStatus.NOT_FOUND));
        return new AvisoViagem(
                this.destino,
                this.dataTermino,
                httpRequest.getRemoteAddr(),
                httpRequest.getHeader(HttpHeaders.USER_AGENT),
                cartao);
    }

}

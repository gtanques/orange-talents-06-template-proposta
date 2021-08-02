package com.orange.proposta.cartoes.bloqueio.dto;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloqueio.DetalheStatusCartao;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

public class DetalheStatusCartaoDTO {

    @NotNull
    private Cartao cartao;

    @NotNull
    private HttpServletRequest request;

    public DetalheStatusCartaoDTO(Cartao cartao, HttpServletRequest request) {
        this.cartao = cartao;
        this.request = request;
    }

    public DetalheStatusCartao toModel(){
        return new DetalheStatusCartao(
                cartao.getStatusCartao(),
                cartao,
                request.getRemoteAddr(),
                request.getHeader(HttpHeaders.USER_AGENT));
    }

}

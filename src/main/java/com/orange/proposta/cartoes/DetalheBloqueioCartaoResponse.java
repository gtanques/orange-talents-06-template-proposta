package com.orange.proposta.cartoes;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

public class DetalheBloqueioCartaoResponse {

    @NotNull
    private Cartao cartao;

    @NotNull
    private HttpServletRequest request;

    public DetalheBloqueioCartaoResponse(Cartao cartao, HttpServletRequest request) {
        this.cartao = cartao;
        this.request = request;
    }

    public DetalheBloqueioCartao toModel(){
        return new DetalheBloqueioCartao(
                cartao.getStatusCartao(),
                cartao,
                request.getRemoteAddr(),
                request.getHeader(HttpHeaders.USER_AGENT));
    }

}

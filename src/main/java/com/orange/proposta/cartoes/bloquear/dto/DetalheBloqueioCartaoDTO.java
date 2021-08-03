package com.orange.proposta.cartoes.bloquear.dto;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloquear.DetalheBloqueioCartao;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

public class DetalheBloqueioCartaoDTO {

    @NotNull
    private Cartao cartao;

    @NotNull
    private HttpServletRequest request;

    public DetalheBloqueioCartaoDTO(Cartao cartao, HttpServletRequest request) {
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

package com.orange.proposta.feign.associarcartoes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartaoResponse {

    private String id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoResponse(String id) {
        this.id = id;
    }

    @Deprecated
    private CartaoResponse() {
    }

    public String getNumeroCartao() {
        return id;
    }

}

package com.orange.proposta.feign.associarcartoes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartaoResponse {

    private final String id;

    @JsonCreator
    public CartaoResponse(String id) {
        this.id = id;
    }

    public String getNumeroCartao() {
        return id;
    }

}

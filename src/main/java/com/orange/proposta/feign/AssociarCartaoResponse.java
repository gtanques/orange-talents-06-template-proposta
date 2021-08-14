package com.orange.proposta.feign;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AssociarCartaoResponse {

    private String id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AssociarCartaoResponse(String id) {
        this.id = id;
    }

    @Deprecated
    private AssociarCartaoResponse() {
    }

    public String getNumeroCartao() {
        return id;
    }

}

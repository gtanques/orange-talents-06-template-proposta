package com.orange.proposta.avisosviagem;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AvisoViagemResponse {

    private final String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoViagemResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

}

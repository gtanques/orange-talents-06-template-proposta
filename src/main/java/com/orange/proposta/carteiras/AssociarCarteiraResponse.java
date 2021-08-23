package com.orange.proposta.carteiras;

public class AssociarCarteiraResponse {

    private String resultado;
    private String id;

    @Deprecated
    private AssociarCarteiraResponse() { }

    public AssociarCarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }

}

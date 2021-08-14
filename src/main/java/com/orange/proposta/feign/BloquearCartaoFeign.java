package com.orange.proposta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "bloquear-cartao", url = "${cartoes.url}")
public interface BloquearCartaoFeign {

    @PostMapping("/{id}/bloqueios")
    void notificarSistemaLegado(Map<String, String> nomeSistema, @PathVariable("id") String numeroCartao);

}

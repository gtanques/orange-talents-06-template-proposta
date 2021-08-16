package com.orange.proposta.feign;

import com.orange.proposta.avisosviagem.AvisoViagemRequest;
import com.orange.proposta.avisosviagem.AvisoViagemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aviso-viagem", url = "${cartoes.url}")
public interface AvisoViagemFeign {

    @PostMapping("/{id}/avisos")
    AvisoViagemResponse avisarViagemSistemaExterno(@PathVariable(name = "id") String numeroCartao, @RequestBody AvisoViagemRequest request);

}

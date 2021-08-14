package com.orange.proposta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name= "solicitacao-analise", url="${analise.url}")
public interface SolicitarAnaliseFeign {

    @PostMapping
    SolicitarAnaliseResponse analiseFinanceira(@RequestBody  @Valid SolicitarAnaliseRequest request);

}

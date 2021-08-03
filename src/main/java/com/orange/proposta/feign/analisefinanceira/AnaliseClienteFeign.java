package com.orange.proposta.feign.analisefinanceira;

import com.orange.proposta.feign.analisefinanceira.dto.SolicitarAnaliseRequest;
import com.orange.proposta.feign.analisefinanceira.dto.SolicitarAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name= "solicitacao-analise", url="${analise.url}")
public interface AnaliseClienteFeign {

    @PostMapping
    SolicitarAnaliseResponse analiseFinanceira(@RequestBody  @Valid SolicitarAnaliseRequest request);

}

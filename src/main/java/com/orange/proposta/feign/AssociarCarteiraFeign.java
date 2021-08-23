package com.orange.proposta.feign;

import com.orange.proposta.carteiras.AssociarCarteiraRequest;
import com.orange.proposta.carteiras.AssociarCarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "associar-carteira", url = "${cartoes.url}")
public interface AssociarCarteiraFeign {

    @PostMapping("/{id}/carteiras")
    AssociarCarteiraResponse associarCartaoNaCarteira(@RequestBody @Valid AssociarCarteiraRequest request, @PathVariable(value = "id")
            String numeroCartao);

}

package com.orange.proposta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "associar-cartoes", url="${cartoes.url}")
public interface AssociarCartaoFeign {

    @GetMapping
    AssociarCartaoResponse associar(@RequestParam(name = "idProposta") String id);

}

package com.orange.proposta.biometrias;

import com.orange.proposta.cartoes.CartaoRepository;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/biometrias")
public class NovaBiometriaController {

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;
    private final Tracer tracer;

    @Autowired
    public NovaBiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository, Tracer tracer) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
        this.tracer = tracer;
    }

    @PostMapping("/{numeroCartao}")
    public ResponseEntity<?> criarBiometria(@RequestBody @Valid NovaBiometriaResquest resquest, @PathVariable String numeroCartao) {
        tracer.activeSpan();
        Biometria biometria = resquest.toModel(cartaoRepository, numeroCartao);
        biometriaRepository.save(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(biometria.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}

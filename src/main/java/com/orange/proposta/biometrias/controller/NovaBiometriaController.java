package com.orange.proposta.biometrias.controller;

import com.orange.proposta.biometrias.Biometria;
import com.orange.proposta.biometrias.dto.NovaBiometriaResquest;
import com.orange.proposta.biometrias.repository.BiometriaRepository;
import com.orange.proposta.cartoes.repository.CartaoRepository;
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

    @Autowired
    public NovaBiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/{numeroCartao}")
    public ResponseEntity<?> criarBiometria(@RequestBody @Valid NovaBiometriaResquest resquest, @PathVariable String numeroCartao) {
        Biometria biometria = resquest.toModel(cartaoRepository, numeroCartao);
        biometriaRepository.save(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(biometria.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}

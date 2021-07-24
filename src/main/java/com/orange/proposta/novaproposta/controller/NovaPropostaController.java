package com.orange.proposta.novaproposta.controller;

import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.dto.NovaPropostaRequest;
import com.orange.proposta.novaproposta.dto.NovaPropostaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas/novo")
public class NovaPropostaController {

    @PersistenceContext
    private final EntityManager entityManager;

    public NovaPropostaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<NovaPropostaResponse> criar(@RequestBody @Valid NovaPropostaRequest request) {
        Proposta proposta = request.toModel();
        entityManager.persist(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new NovaPropostaResponse(proposta));
    }

}

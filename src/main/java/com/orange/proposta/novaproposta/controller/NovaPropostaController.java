package com.orange.proposta.novaproposta.controller;

import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.dto.NovaPropostaRequest;
import com.orange.proposta.novaproposta.dto.NovaPropostaResponse;
import com.orange.proposta.novaproposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas/novo")
public class NovaPropostaController {

    private final PropostaRepository propostaRepository;

    @Autowired
    public NovaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<NovaPropostaResponse> criar(@RequestBody @Valid NovaPropostaRequest request) {
        Proposta proposta = request.toModel(propostaRepository);
        propostaRepository.save(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new NovaPropostaResponse(proposta));
    }

}

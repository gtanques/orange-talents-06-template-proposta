package com.orange.proposta.propostas;

import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("propostas/acompanharproposta")
public class AcompanharPropostaController {

    private final PropostaRepository propostaRepository;

    @Autowired
    public AcompanharPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> acompanharProposta(@PathVariable String id){
        Proposta existeProposta = propostaRepository
                .findById(id)
                .orElseThrow(() -> new ExceptionPersonalizada("Proposta: " + id + " não encontrada", HttpStatus.NOT_FOUND));

        AcompanharPropostaResponse response = new AcompanharPropostaResponse(existeProposta);

        return  ResponseEntity.ok().body(response);
    }

}

package com.orange.proposta.novaproposta.controller;

import com.orange.proposta.analises.AnaliseClienteFeign;
import com.orange.proposta.analises.dto.SolicitarAnaliseRequest;
import com.orange.proposta.analises.dto.SolicitarAnaliseResponse;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.dto.NovaPropostaRequest;
import com.orange.proposta.novaproposta.dto.NovaPropostaResponse;
import com.orange.proposta.novaproposta.repository.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final AnaliseClienteFeign analiseClienteFeign;

    @Autowired
    public NovaPropostaController(PropostaRepository propostaRepository, AnaliseClienteFeign analiseClienteFeign) {
        this.propostaRepository = propostaRepository;
        this.analiseClienteFeign = analiseClienteFeign;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request) {

        Proposta proposta = request.toModel(propostaRepository);
        propostaRepository.save(proposta);

        try {
            SolicitarAnaliseRequest analiseRequest = new SolicitarAnaliseRequest(proposta);
            SolicitarAnaliseResponse response = analiseClienteFeign.analiseFinanceira(analiseRequest);
            proposta.definirStatus(response.getResultadoSolicitacao());
        } catch (FeignException.UnprocessableEntity e) {
            proposta.definirStatus("COM_RESTRICAO");
        }catch (FeignException e){
            throw new ExceptionPersonalizada("Serviço de análise indisponível", HttpStatus.SERVICE_UNAVAILABLE);
        }

        /*
          Update para salvar o status da proposta
         */
        propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new NovaPropostaResponse(proposta));
    }

}

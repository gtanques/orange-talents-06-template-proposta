package com.orange.proposta.propostas.novaproposta.controller;

import com.orange.proposta.feign.analisefinanceira.AnaliseClienteFeign;
import com.orange.proposta.feign.analisefinanceira.dto.SolicitarAnaliseRequest;
import com.orange.proposta.feign.analisefinanceira.dto.SolicitarAnaliseResponse;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.propostas.novaproposta.Proposta;
import com.orange.proposta.propostas.novaproposta.dto.NovaPropostaRequest;
import com.orange.proposta.propostas.novaproposta.dto.NovaPropostaResponse;
import com.orange.proposta.propostas.novaproposta.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/propostas/")
public class NovaPropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseClienteFeign analiseClienteFeign;
    private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);

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
            proposta.AdicionaStatusFinanceiroNaProposta(response.getResultadoSolicitacao());
        } catch (FeignException.UnprocessableEntity e) {
            proposta.AdicionaStatusFinanceiroNaProposta("COM_RESTRICAO");
        }catch (FeignException e){
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Serviço de análise indisponível", HttpStatus.SERVICE_UNAVAILABLE);
        }

        /*
          Update para salvar o status da proposta
         */
        propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/acompanharproposta/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new NovaPropostaResponse(proposta));
    }

}

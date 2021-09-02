package com.orange.proposta.propostas;

import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import com.orange.proposta.feign.SolicitarAnaliseFeign;
import com.orange.proposta.feign.SolicitarAnaliseRequest;
import com.orange.proposta.feign.SolicitarAnaliseResponse;
import feign.FeignException;
import io.opentracing.Tracer;
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
    private final SolicitarAnaliseFeign solicitarAnaliseFeign;
    private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
    private final Tracer tracer;

    @Autowired
    public NovaPropostaController(PropostaRepository propostaRepository, SolicitarAnaliseFeign solicitarAnaliseFeign, Tracer tracer) {
        this.propostaRepository = propostaRepository;
        this.solicitarAnaliseFeign = solicitarAnaliseFeign;
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request) {
        tracer.activeSpan();

        Proposta proposta = request.toModel(propostaRepository);
        propostaRepository.save(proposta);

        try {
            SolicitarAnaliseRequest analiseRequest = new SolicitarAnaliseRequest(proposta);
            SolicitarAnaliseResponse response = solicitarAnaliseFeign.analiseFinanceira(analiseRequest);
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

package com.orange.proposta.cartoes;

import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import com.orange.proposta.feign.BloquearCartaoFeign;
import feign.FeignException;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("cartoes/bloquear")
public class BloquearCartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloquearCartaoFeign bloquearCartaoFeign;
    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);
    private final Tracer tracer;

    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public BloquearCartaoController(CartaoRepository repository, EntityManager entityManager, BloquearCartaoFeign bloquearCartaoFeign, Tracer tracer) {
        this.cartaoRepository = repository;
        this.entityManager = entityManager;
        this.bloquearCartaoFeign = bloquearCartaoFeign;

        this.tracer = tracer;
    }

    @PutMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@Valid @RequestBody BloquearCartaoRequest request,
                                            @PathVariable String numeroCartao,
                                            HttpServletRequest httpRequest) {
        tracer.activeSpan();
        Cartao cartao = request.toModel(cartaoRepository, numeroCartao);
        DetalheBloqueioCartaoResponse informacaoDTO = new DetalheBloqueioCartaoResponse(cartao, httpRequest);

        cartaoRepository.save(cartao);
        entityManager.persist(informacaoDTO.toModel());

        try {
            bloquearCartaoFeign.notificarSistemaLegado(Map.of("sistemaResponsavel", "Proposta-API"), numeroCartao);
            logger.info("Cartão bloqueado!");
        } catch (FeignException e) {
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Ocorreu um erro no serviço de bloqueio.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().build();
    }

}

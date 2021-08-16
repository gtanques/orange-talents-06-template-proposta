package com.orange.proposta.avisosviagem;

import com.orange.proposta.cartoes.CartaoRepository;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import com.orange.proposta.feign.AvisoViagemFeign;
import feign.FeignException;
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

@RestController
@RequestMapping("/cartoes/avisoviagem")
public class AvisoViagemController {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemFeign avisoViagemFeign;
    private Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @Autowired
    public AvisoViagemController(EntityManager entityManager, CartaoRepository cartaoRepository, AvisoViagemFeign avisoViagemFeign) {
        this.entityManager = entityManager;
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemFeign = avisoViagemFeign;
    }

    @PostMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> avisarViagem(@Valid @RequestBody AvisoViagemRequest request,
                                          @PathVariable String numeroCartao, HttpServletRequest httpRequest) {
        AvisoViagem avisoViagem = request.toModel(httpRequest, cartaoRepository, numeroCartao);

        try {
            AvisoViagemResponse viajemConfimada = avisoViagemFeign.avisarViagemSistemaExterno(numeroCartao, request);
            logger.info("Aviso viajem: " + viajemConfimada.getResultado());
            if (viajemConfimada.getResultado() == "CRIADO"){
                entityManager.persist(avisoViagem);
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Falha ao confirmar viajem.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().build();
    }

}

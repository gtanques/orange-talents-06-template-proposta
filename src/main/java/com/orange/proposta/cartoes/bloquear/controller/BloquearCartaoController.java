package com.orange.proposta.cartoes.bloquear.controller;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloquear.dto.BloquearCartaoRequest;
import com.orange.proposta.cartoes.bloquear.dto.DetalheStatusCartaoDTO;
import com.orange.proposta.cartoes.repository.CartaoRepository;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.feign.bloquearcartao.BloquearCartaoFeign;
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
import java.util.Map;

@RestController
@RequestMapping("cartoes/bloquear")
public class BloquearCartaoController {

    private final CartaoRepository cartaoRepository;
    private final BloquearCartaoFeign bloquearCartaoFeign;
    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public BloquearCartaoController(CartaoRepository repository, EntityManager entityManager, BloquearCartaoFeign bloquearCartaoFeign) {
        this.cartaoRepository = repository;
        this.entityManager = entityManager;
        this.bloquearCartaoFeign = bloquearCartaoFeign;

    }

    @PutMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@Valid @RequestBody BloquearCartaoRequest request,
                                            @PathVariable String numeroCartao, HttpServletRequest httpRequest) {
        Cartao cartao = request.toModel(cartaoRepository, numeroCartao);
        DetalheStatusCartaoDTO informacaoDTO = new DetalheStatusCartaoDTO(cartao, httpRequest);

        cartaoRepository.save(cartao);
        entityManager.persist(informacaoDTO.toModel());

        try {
            bloquearCartaoFeign.notificarSistemaLegado(Map.of("sistemaResponsavel", "Proposta-API"), numeroCartao);
            logger.info("Cartão bloqueado!");
        }catch (FeignException e){
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Ocorreu um erro no serviço de bloqueio.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok().build();
    }

}

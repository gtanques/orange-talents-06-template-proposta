package com.orange.proposta.cartoes.bloqueio.controller;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.bloqueio.dto.AlterarStatusCartaoRequest;
import com.orange.proposta.cartoes.bloqueio.dto.DetalheStatusCartaoDTO;
import com.orange.proposta.cartoes.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("cartoes/status")
public class AlterarStatusCartaoController {

    @Autowired
    private final CartaoRepository cartaoRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public AlterarStatusCartaoController(CartaoRepository repository, EntityManager entityManager) {
        this.cartaoRepository = repository;
        this.entityManager = entityManager;
    }

    @PutMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> alterarStatusCartao(@Valid @RequestBody AlterarStatusCartaoRequest request,
                                                 @PathVariable String numeroCartao, HttpServletRequest httpRequest){
        Cartao cartao = request.toModel(cartaoRepository, numeroCartao);
        DetalheStatusCartaoDTO informacaoDTO = new DetalheStatusCartaoDTO(cartao, httpRequest);

        cartaoRepository.save(cartao);
        entityManager.persist(informacaoDTO.toModel());
        return ResponseEntity.ok().build();
    }

}

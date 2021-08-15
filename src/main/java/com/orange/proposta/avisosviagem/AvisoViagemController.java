package com.orange.proposta.avisosviagem;

import com.orange.proposta.cartoes.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AvisoViagemController(EntityManager entityManager, CartaoRepository cartaoRepository) {
        this.entityManager = entityManager;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> avisarViagem(@Valid @RequestBody AvisoViagemRequest request,
                                          @PathVariable String numeroCartao, HttpServletRequest httpRequest){
        AvisoViagem avisoViagem = request.toModel(httpRequest, cartaoRepository, numeroCartao);
        entityManager.persist(avisoViagem);
        return ResponseEntity.ok().build();
    }

}

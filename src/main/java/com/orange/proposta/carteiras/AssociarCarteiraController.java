package com.orange.proposta.carteiras;

import com.orange.proposta.cartoes.CartaoRepository;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import com.orange.proposta.feign.AssociarCarteiraFeign;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cartoes/carteiras")
public class AssociarCarteiraController {

    private final CartaoRepository cartaoRepository;
    private final AssociarCarteiraFeign associarCarteiraFeign;
    private final Logger logger = LoggerFactory.getLogger(AssociarCarteiraController.class);
    private final CarteiraRepository carteiraRepository;

    @Autowired
    public AssociarCarteiraController(CartaoRepository cartaoRepository, AssociarCarteiraFeign associarCarteiraFeign, CarteiraRepository carteiraRepository) {
        this.cartaoRepository = cartaoRepository;
        this.associarCarteiraFeign = associarCarteiraFeign;
        this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/{numeroCartao}")
    @Transactional
    public ResponseEntity<?> adicionarCarteira(@RequestBody @Valid AssociarCarteiraRequest request, @PathVariable String numeroCartao) {
        Carteira carteira = request.paraCarteira(numeroCartao, cartaoRepository, carteiraRepository);
        try {
            AssociarCarteiraResponse response = associarCarteiraFeign
                    .associarCartaoNaCarteira(request, numeroCartao);
            logger.info(response.getResultado());
        } catch (FeignException e) {
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Falha ao vincular cartão na carteira", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        carteiraRepository.save(carteira);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(carteira.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}

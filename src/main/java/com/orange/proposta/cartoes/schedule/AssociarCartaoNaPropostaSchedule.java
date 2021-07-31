package com.orange.proposta.cartoes.schedule;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.feign.associarcartoes.AssociarCartaoFeign;
import com.orange.proposta.feign.associarcartoes.dto.CartaoResponse;
import com.orange.proposta.propostas.novaproposta.Proposta;
import com.orange.proposta.propostas.novaproposta.enumerador.StatusFinanceiro;
import com.orange.proposta.propostas.novaproposta.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Component
@EnableAsync
@EnableScheduling
public class AssociarCartaoNaPropostaSchedule {

    private final AssociarCartaoFeign associarCartaoFeign;
    private final PropostaRepository propostaRepository;
    private final Logger logger = LoggerFactory.getLogger(AssociarCartaoNaPropostaSchedule.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AssociarCartaoNaPropostaSchedule(AssociarCartaoFeign associarCartaoFeign, PropostaRepository propostaRepository) {
        this.associarCartaoFeign = associarCartaoFeign;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void associarCartoesAprovados() {

        List<Proposta> propostas = propostaRepository.buscarPropostasParaVincularCartao(StatusFinanceiro.ELEGIVEL);

        if(!propostas.isEmpty()){
            propostas.forEach(this::vinculaCartaoNaPropostaESalva);
        }

    }

    private void vinculaCartaoNaPropostaESalva(Proposta proposta) {
        try {
            CartaoResponse cartaoResponse = associarCartaoFeign.associar(proposta.getId());

            Cartao cartao = CriaESalvaOCartao(cartaoResponse.getNumeroCartao());

            proposta.adicionaCartaoNaProposta(cartao);
            propostaRepository.save(proposta);

            logger.info("Cartão vinculado!");
        } catch (FeignException e) {
            logger.error(e.getMessage());
            throw new ExceptionPersonalizada("Ocorreu um erro no serviço para vincular cartão", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private Cartao CriaESalvaOCartao(String numeroCartao) {
        Cartao cartao = new Cartao(numeroCartao);
        entityManager.persist(cartao);
        return cartao;
    }

}

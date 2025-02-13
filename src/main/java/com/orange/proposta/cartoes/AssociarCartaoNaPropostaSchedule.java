package com.orange.proposta.cartoes;

import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import com.orange.proposta.feign.AssociarCartaoFeign;
import com.orange.proposta.feign.AssociarCartaoResponse;
import com.orange.proposta.propostas.Proposta;
import com.orange.proposta.propostas.PropostaRepository;
import com.orange.proposta.propostas.StatusFinanceiroEnum;
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
    private final EntityManager entityManager;

    @Autowired
    public AssociarCartaoNaPropostaSchedule(AssociarCartaoFeign associarCartaoFeign, PropostaRepository propostaRepository, EntityManager entityManager) {
        this.associarCartaoFeign = associarCartaoFeign;
        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void associarCartoesAprovados() {

        List<Proposta> propostas = propostaRepository.buscarPropostasParaVincularCartao(StatusFinanceiroEnum.ELEGIVEL);

        if(!propostas.isEmpty()){
            propostas.forEach(this::vinculaCartaoNaPropostaESalva);
        }

    }

    private void vinculaCartaoNaPropostaESalva(Proposta proposta) {
        try {
            AssociarCartaoResponse associarCartaoResponse = associarCartaoFeign.associar(proposta.getId());

            Cartao cartao = CriaESalvaOCartao(associarCartaoResponse.getNumeroCartao());

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

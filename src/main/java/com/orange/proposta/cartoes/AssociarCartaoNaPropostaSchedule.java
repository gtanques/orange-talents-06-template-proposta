package com.orange.proposta.cartoes;

import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.configuracoes.loggers.Log;
import com.orange.proposta.feign.associarcartoes.AssociarCartaoFeign;
import com.orange.proposta.feign.associarcartoes.dto.CartaoResponse;
import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.enumerador.StatusFinanceiro;
import com.orange.proposta.novaproposta.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;


@Component
@EnableAsync
@EnableScheduling
public class AssociarCartaoNaPropostaSchedule {

    private final AssociarCartaoFeign associarCartaoFeign;
    private final PropostaRepository propostaRepository;
    private final Logger logger = LoggerFactory.getLogger(Log.class);

    @Autowired
    public AssociarCartaoNaPropostaSchedule(AssociarCartaoFeign associarCartaoFeign, PropostaRepository propostaRepository) {
        this.associarCartaoFeign = associarCartaoFeign;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedRate = 120000)
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
            proposta.adicionaCartaoNaProposta(cartaoResponse.getNumeroCartao());
            propostaRepository.save(proposta);
            logger.info("Cartões vinculados!");
        } catch (FeignException e) {
            throw new ExceptionPersonalizada("Ocorreu um erro no serviço para vincular cartão", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}

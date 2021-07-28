package com.orange.proposta.cartoes;

import com.orange.proposta.configuracoes.exceptions.ExceptionPersonalizada;
import com.orange.proposta.feign.associarcartoes.AssociarCartaoFeign;
import com.orange.proposta.feign.associarcartoes.dto.CartaoResponse;
import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.enumerador.StatusFinanceiro;
import com.orange.proposta.novaproposta.repository.PropostaRepository;
import feign.FeignException;
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
public class AssociarCartaoSchedule {

    private final AssociarCartaoFeign associarCartaoFeign;
    private final PropostaRepository propostaRepository;

    @Autowired
    public AssociarCartaoSchedule(AssociarCartaoFeign associarCartaoFeign, PropostaRepository propostaRepository) {
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
            proposta.definirNumeroCartao(cartaoResponse.getNumeroCartao());
        } catch (FeignException e) {
            throw new ExceptionPersonalizada("Serviço para vincular cartão indisponível", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}

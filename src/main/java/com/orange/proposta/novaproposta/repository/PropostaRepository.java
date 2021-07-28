package com.orange.proposta.novaproposta.repository;

import com.orange.proposta.novaproposta.Proposta;
import com.orange.proposta.novaproposta.enumerador.StatusFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByCpfCnpj(String cpfCnpj);

    @Query("select p from Proposta p where p.statusFinanceiro=:elegivel and p.numeroCartao=null")
    List<Proposta> buscarPropostasParaVincularCartao(StatusFinanceiro elegivel);

}

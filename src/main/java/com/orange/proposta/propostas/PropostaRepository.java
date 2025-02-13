package com.orange.proposta.propostas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, String> {

    Optional<Proposta> findByCpfCnpj(String cpfCnpj);

    @Query("select p from Proposta p where p.statusFinanceiro=:elegivel and p.cartao=null")
    List<Proposta> buscarPropostasParaVincularCartao(StatusFinanceiroEnum elegivel);

}

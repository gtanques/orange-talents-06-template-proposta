package com.orange.proposta.novaproposta.repository;

import com.orange.proposta.novaproposta.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByCpfCnpj(String cpfCnpj);

}

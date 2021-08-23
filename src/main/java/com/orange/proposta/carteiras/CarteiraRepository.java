package com.orange.proposta.carteiras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarteiraRepository extends JpaRepository<Carteira, String> {

    @Query("SELECT COUNT(c) > 0 FROM Carteira c WHERE c.cartao.numeroCartao=:numeroCartao AND tipoCarteira=:tipoCarteira")
    boolean existeCarteiraCadastrada(String numeroCartao, TipoCarteiraEnum tipoCarteira);

}

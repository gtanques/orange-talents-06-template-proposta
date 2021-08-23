package com.orange.proposta.carteiras;

import com.orange.proposta.cartoes.Cartao;
import com.orange.proposta.cartoes.CartaoRepository;
import com.orange.proposta.configuracoes.ExceptionPersonalizada;
import org.springframework.http.HttpStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AssociarCarteiraRequest {

    @NotBlank
    @NotNull
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoCarteiraEnum carteira;

    public AssociarCarteiraRequest(String email, TipoCarteiraEnum carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira paraCarteira(String numeroCartao, CartaoRepository cartaoRepository, CarteiraRepository carteiraRepository){
        boolean carteiraCadastrada = carteiraRepository.existeCarteiraCadastrada(numeroCartao, this.carteira);
        if(carteiraCadastrada){
            throw new ExceptionPersonalizada("Carteira já vinculada no cartão.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Cartao cartao = cartaoRepository
                .findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new ExceptionPersonalizada("Cartão não encontrado.", HttpStatus.NOT_FOUND));
        return new Carteira(this.email, this.carteira, cartao);
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteiraEnum getCarteira() {
        return carteira;
    }

}

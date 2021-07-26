package com.orange.proposta.configuracoes.exceptions.annotation.cpfcnpj;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PossuiPropostaValidator implements ConstraintValidator<PossuiPropostaValid, Object> {


    @PersistenceContext
    private final EntityManager entityManager;


    public PossuiPropostaValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        if (obj == null) {
            return true;
        }

        Query query = entityManager.createQuery("select 1 from  Proposta p where p.cpfCnpj=:value");
        query.setParameter("value", obj);
        List<?> list = query.getResultList();

        return list.isEmpty();

    }

}

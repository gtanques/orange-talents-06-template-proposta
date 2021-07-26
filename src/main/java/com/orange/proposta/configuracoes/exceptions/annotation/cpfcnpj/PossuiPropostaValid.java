package com.orange.proposta.configuracoes.exceptions.annotation.cpfcnpj;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;


@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PossuiPropostaValidator.class)
public @interface PossuiPropostaValid {

    String message() default "Já existe uma proposta cadastrada para esse CPF/CNPJ.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.orange.proposta.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {BiometriaBase64Validator.class})
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface BiometriaBase64Valid {

    String message() default "O fingerprint deve estar em base64.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

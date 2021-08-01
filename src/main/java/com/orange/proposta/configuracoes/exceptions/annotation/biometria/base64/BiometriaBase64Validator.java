package com.orange.proposta.configuracoes.exceptions.annotation.biometria.base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class BiometriaBase64Validator implements ConstraintValidator <BiometriaBase64Valid, String>{

    private final Logger logger = LoggerFactory.getLogger(BiometriaBase64Validator.class);

    @Override
    public boolean isValid(String biometria, ConstraintValidatorContext context) {
        return vericaSeABiometriaEstaEmBase64(biometria);
    }

    private boolean vericaSeABiometriaEstaEmBase64(String biometria) {
        try {
            byte[] decode = Base64.getDecoder().decode(biometria.getBytes());
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

}

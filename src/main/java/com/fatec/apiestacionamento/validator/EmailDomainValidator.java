package com.fatec.apiestacionamento.validator;

import com.fatec.apiestacionamento.annotation.EmailFatec;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<EmailFatec, String> {

    @Override
    public void initialize(EmailFatec constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.endsWith("@fatec.sp.gov.br");
    }
}
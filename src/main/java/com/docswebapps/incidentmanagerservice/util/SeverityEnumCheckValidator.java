package com.docswebapps.incidentmanagerservice.util;

import com.docswebapps.incidentmanagerservice.domain.enumeration.Severity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SeverityEnumCheckValidator implements ConstraintValidator<SeverityEnumCheck, Severity> {
    private Severity[] subset;

    @Override
    public void initialize(SeverityEnumCheck constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Severity value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}


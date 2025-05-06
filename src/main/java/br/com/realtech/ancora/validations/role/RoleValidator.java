package br.com.realtech.ancora.validations.role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private static final Set<String> VALID_ROLES = Set.of("USER", "ADMIN");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return VALID_ROLES.contains(value.toUpperCase());
    }
}

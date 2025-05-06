package br.com.realtech.ancora.validations.birthdate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthdateValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {
    String message() default "Enter a valid birthdate.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package fr.eni.encheres.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DatesValidator.class)
@Target({ElementType.TYPE}) // La validation s'applique à la classe entière
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDates {
    String message() default "La date de fin doit être strictement postérieure à la date de début.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
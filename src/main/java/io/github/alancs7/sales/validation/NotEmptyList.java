package io.github.alancs7.sales.validation;

import io.github.alancs7.sales.validation.constraint.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "List cannot be empty.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

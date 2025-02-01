package com.example.schedulefundtransfer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class) // Points to the validator class
public @interface ValidDateFormat {
    String message() default "Invalid date format. Use 'yyyy-MM-dd'T'HH:mm:ss.SSSSSS'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

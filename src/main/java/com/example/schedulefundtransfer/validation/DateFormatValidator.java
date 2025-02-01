package com.example.schedulefundtransfer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            // Attempt to parse the value using the specific format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDateTime.parse(value, formatter); // Will throw exception if invalid format
            return true; // Valid if no exception is thrown
        } catch (DateTimeParseException e) {
            return false; // Invalid format if exception is thrown
        }
    }
}

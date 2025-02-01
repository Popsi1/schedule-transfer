package com.example.schedulefundtransfer.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {

    // List of possible date formats
    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (String format : DATE_FORMATS) {
            try {
                // Try parsing the date using the current format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDateTime dateTime = LocalDateTime.parse(value, formatter);

                // Check if the parsed date is after the current date/time
                return dateTime.isAfter(LocalDateTime.now());
            } catch (Exception e) {
                // If parsing fails, continue to the next format
            }
        }

        // If no format matched, return false
        return false;
    }
}
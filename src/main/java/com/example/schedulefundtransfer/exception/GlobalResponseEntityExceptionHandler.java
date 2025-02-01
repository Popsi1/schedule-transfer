package com.example.schedulefundtransfer.exception;

import com.example.schedulefundtransfer.util.DataResponseUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

        @Nullable
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                BindingResult bindingResult = ex.getBindingResult();

                Map<String, String> fieldErrors = new HashMap<>();
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                        String fieldName = fieldError.getField();
                        String errorMessage = fieldError.getDefaultMessage();
                        fieldErrors.put(fieldName, errorMessage);
                }

                return ResponseEntity.badRequest().body(fieldErrors);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<Object> badRequestException(BadRequestException e) {
                return ResponseEntity.badRequest().body(DataResponseUtils.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataResponseUtils.errorResponse(e.getMessage(), HttpStatus.NOT_FOUND));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleException(Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DataResponseUtils.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
}


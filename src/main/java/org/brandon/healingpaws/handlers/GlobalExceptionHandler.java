package org.brandon.healingpaws.handlers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.brandon.healingpaws.exceptions.ClientAlreadyExistsException;
import org.brandon.healingpaws.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ProblemDetail handleNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        log.error("Entity with id [{}] not found", ex.getId(), ex);

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());

        problemDetails.setType(URI.create("http://localhost:8080/errors/entity-not-found"));
        problemDetails.setTitle("Entity Not Found");
        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setProperty("id", ex.getId());
        problemDetails.setInstance(URI.create(req.getServletPath()));

        return problemDetails;
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ProblemDetail handleAlreadyExistsException(HttpServletRequest req, ClientAlreadyExistsException ex) {
        log.error("Client with email [{}] already exists", ex.getEmail(), ex);

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getLocalizedMessage());

        problemDetails.setType(URI.create("http://localhost:8080/errors/customer-already-exists"));
        problemDetails.setTitle("Entity Already Exists");
        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setProperty("email", ex.getEmail());
        problemDetails.setInstance(URI.create(req.getServletPath()));

        return problemDetails;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ProblemDetail handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        log.error("Validation failed", ex);

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetails.setType(URI.create("http://localhost:8080/errors/validation-failed"));
        problemDetails.setTitle("Validation Failed");
        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setProperty("validation_errors", errorMap);
        problemDetails.setInstance(URI.create(req.getServletPath()));

        return problemDetails;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ProblemDetail handleGenericException(HttpServletRequest req, Exception ex) {
        log.error("An unexpected error occurred", ex);

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        problemDetails.setType(URI.create("http://localhost:8080/errors/internal-server-error"));
        problemDetails.setTitle("Internal Server Error");
        problemDetails.setProperty("timestamp", LocalDateTime.now());
        problemDetails.setInstance(URI.create(req.getServletPath()));

        return problemDetails;
    }

}

package io.github.kiransr99.parg.exceptions;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalErrorResponse;
import io.github.kiransr99.parg.exceptions.custom.GeneralException;
import io.github.kiransr99.parg.exceptions.custom.InvalidEmailException;
import io.github.kiransr99.parg.exceptions.custom.InvalidPasswordException;
import io.github.kiransr99.parg.exceptions.custom.InvalidPhoneFormat;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling extends BaseController {

    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handlingGeneralException(GeneralException exception){
        log.error("Handling GeneralException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GlobalErrorResponse> handlingEntityNotFoundException(EntityNotFoundException exception){
        log.error("Handling EntityNotFoundException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handlingDataIntegrityViolationException(DataIntegrityViolationException exception){
        log.error("Handling DataIntegrityViolationException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, "Data integrity violation occurred", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<GlobalErrorResponse> handlingAccessDeniedException(AccessDeniedException exception){
        log.error("Handling AccessDeniedException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.FORBIDDEN, "Access denied", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalErrorResponse> handlingIOException(IOException exception){
        log.error("Handling IOException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Input/output error", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handlingIllegalArgumentException (IllegalArgumentException exception) {
        log.error("Handling IllegalArgumentException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, "Illegal argument", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalErrorResponse> handlingNullPointerException (NullPointerException exception) {
        log.error("Handling NullPointerException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Null pointer exception", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handlingConstraintViolationException (ConstraintViolationException exception) {
        log.error("Handling ConstraintViolationException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, "Constraint violation", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(HttpClientErrorException.MethodNotAllowed.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<GlobalErrorResponse> handlingMethodNotAllowedException (HttpClientErrorException.MethodNotAllowed exception) {
        log.error("Handling MethodNotAllowedException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GlobalErrorResponse> handleUnauthorizedException (HttpClientErrorException.Unauthorized exception) {
        log.error("Handling UnauthorizedException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handleInvalidEmailException (InvalidEmailException exception) {
        log.error("Handling InvalidEmailException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, "Invalid Email", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handleInvalidPasswordException (InvalidPasswordException exception) {
        log.error("Handling InvalidPasswordException: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, "Invalid Password", exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(InvalidPhoneFormat.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handleInvalidPhoneFormat (InvalidPhoneFormat exception) {
        log.error("Handling InvalidPhoneFormat: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Handling MethodArgumentNotValidException: {}", exception.getMessage(), exception);

        // Get the validation message from the annotation
        String validationMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.BAD_REQUEST, validationMessage, exception);
        log.debug("Response: {}", response);
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalErrorResponse> handleException (Exception exception) {
        log.error("Handling Exception: {}", exception.getMessage(), exception);
        ResponseEntity<GlobalErrorResponse> response = errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", exception);
        log.debug("Response: {}", response);
        return response;
    }
}
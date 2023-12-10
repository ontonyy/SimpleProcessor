package com.simple.error.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.simple.models.exception.ErrorMessage;
import com.simple.models.exception.SimpleException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value={NoHandlerFoundException.class})
    @ResponseStatus(code=HttpStatus.NOT_FOUND)
    public ErrorMessage badRequest(NoHandlerFoundException ex, WebRequest request) {
        loggingErrorInfo(ex, request);

        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "This url path is not found, please try to use different link");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        loggingErrorInfo(ex, request);

        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                "General exception that can be thrown in unexpectable moment");
    }

    @ExceptionHandler(value = SimpleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(SimpleException ex, WebRequest request) {
        loggingErrorInfo(ex, request);

        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                "Custom exception that was thrown manually by service");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        loggingErrorInfo(ex, request);

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "Constraint violation error, please check parameters of request bodies");
    }

    private void loggingErrorInfo(Exception exception, WebRequest request) {
        log.error("Info about exception: {}", request.getDescription(true), exception);
    }
}
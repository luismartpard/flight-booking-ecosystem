package com.lmp.flightbookingcommon.infrastructure.web.exception;

import com.lmp.flightbookingcommon.common.exception.ApplicationException;
import com.lmp.flightbookingcommon.common.exception.BadRequestException;
import com.lmp.flightbookingcommon.common.exception.MessageNotReadableException;
import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.flightbookingcommon.infrastructure.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private final ExceptionStatusResolver statusResolver = new ExceptionStatusResolver();

    private final HttpErrorCodeResolver errorCodeResolver  = new HttpErrorCodeResolver();

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex,
            Locale locale,
            HttpServletRequest request
    ) {

        HttpStatus status = statusResolver.resolve(ex);

        String message = messageSource.getMessage(
                ex.getMessageKey(),
                ex.getArgs(),
                ex.getMessageKey(),
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                status.value(),
                errorCodeResolver.resolve(status),
                ex.getMessageKey(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException ex,
            Locale locale,
            HttpServletRequest request
    ) {

        return handleApplicationException(
                new MessageNotReadableException("error.request.malformed_json"),
                locale,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException (
            ConstraintViolationException ex,
            Locale locale,
            HttpServletRequest request
    ) {

        return handleApplicationException(
                new BadRequestException("error.request.constraint_violation"),
                locale,
                request
        );

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            NoResourceFoundException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        return handleApplicationException(
                new ResourceNotFoundException("error.request.resource_not_found"),
                locale,
                request
        );
    }

}
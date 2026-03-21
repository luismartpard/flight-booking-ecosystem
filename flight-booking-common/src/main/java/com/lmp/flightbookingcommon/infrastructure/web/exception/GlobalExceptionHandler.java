package com.lmp.flightbookingcommon.infrastructure.web.exception;

import com.lmp.flightbookingcommon.common.exception.BadRequestException;
import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.flightbookingcommon.infrastructure.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 404 - Recursos inexistentes (entity no encontrada en BBDD)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String message = messageSource.getMessage(
                ex.getMessageKey(),
                ex.getArgs(),
                ex.getMessageKey(),
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessageKey(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * 400 - Errores semánticos / reglas de negocio / parámetros inválidos (custom)
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String message = messageSource.getMessage(
                ex.getMessageKey(),
                ex.getArgs(),
                ex.getMessageKey(),
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessageKey(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 400 - Parámetro con tipo inválido (ej: enum mal, número donde va texto, etc.)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String message = messageSource.getMessage(
                "error.request.type_mismatch",
                new Object[]{ex.getName(), ex.getValue()},
                "Invalid value for parameter '%s': %s".formatted(ex.getName(), ex.getValue()),
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "error.request.type_mismatch",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 400 - Validación en path/query params (jakarta validation)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String details = ex.getConstraintViolations().stream()
                .map(v -> "%s: %s (invalid: %s)".formatted(
                        simplifyPath(v.getPropertyPath().toString()),
                        v.getMessage(),
                        v.getInvalidValue()
                ))
                .collect(Collectors.joining("; "));

        String message = messageSource.getMessage(
                "error.request.constraint_violation",
                new Object[]{details},
                details,
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "error.request.constraint_violation",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 400 - Validación de @RequestBody con @Valid (DTO)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String fieldDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        String globalDetails = ex.getBindingResult().getGlobalErrors().stream()
                .map(e -> e.getObjectName() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));

        String details = joinNonBlank(fieldDetails, globalDetails);

        String message = messageSource.getMessage(
                "validation.error",
                new Object[]{details},
                details,
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "validation.error",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            Locale locale,
            HttpServletRequest request
    ) {

        String message = messageSource.getMessage(
                "error.request.malformed_json",
                null,
                "Malformed JSON request",
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "error.request.malformed_json",
                message,
                request.getRequestURI()
        );


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }


    /**
     * 500 - Fallback para errores no controlados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            Locale locale,
            HttpServletRequest request
    ) {
        String message = messageSource.getMessage(
                "error.internal",
                null,
                "Internal server error",
                locale
        );

        ErrorResponse body = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error.internal",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String formatFieldError(FieldError fe) {
        return fe.getField() + ": " + fe.getDefaultMessage();
    }

    private String simplifyPath(String propertyPath) {
        int lastDot = propertyPath.lastIndexOf('.');
        return lastDot >= 0 ? propertyPath.substring(lastDot + 1) : propertyPath;
    }

    private String joinNonBlank(String a, String b) {
        boolean aBlank = (a == null || a.isBlank());
        boolean bBlank = (b == null || b.isBlank());
        if (aBlank && bBlank) return "";
        if (aBlank) return b;
        if (bBlank) return a;
        return a + "; " + b;
    }
}
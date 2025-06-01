package com.estoque.sistemaestoque.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(401).body(new ErrorResponse("Credenciais inválidas"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String message = "Erro de formato no parâmetro '" + ex.getName() + "'. ";
        if (ex.getRequiredType() != null) {
            if (ex.getRequiredType().equals(java.time.LocalDateTime.class)) {
                message += "Use o formato: yyyy-MM-ddTHH:mm:ss (exemplo: 2025-06-01T16:14:14)";
            } else {
                message += "Formato inválido para o tipo " + ex.getRequiredType().getSimpleName();
            }
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }
}
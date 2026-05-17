package com.tavernhub.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarObjetoNaoEncontrado(
            ObjetoNaoEncontradoException ex,
            HttpServletRequest request) {

        ErroResposta erro = new ErroResposta(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}
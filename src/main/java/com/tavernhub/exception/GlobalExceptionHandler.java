package com.tavernhub.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId; // Importado para garantir a sincronização regional
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Centraliza a geração do timestamp sincronizado com o Horário de Brasília (UTC-3)
    private LocalDateTime obterTimestampBrasilia() {
        return LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

    // 1. Captura quando o recurso não é encontrado (Fase 3.5)
    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarObjetoNaoEncontrado(
            ObjetoNaoEncontradoException ex,
            HttpServletRequest request) {

        ErroResposta erro = new ErroResposta(
                obterTimestampBrasilia(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 2. Captura erros de validação disparados nas Controllers através do @Valid (Fase 4)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarErroValidacaoMecanica(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErroResposta erro = new ErroResposta(
                obterTimestampBrasilia(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação nos Dados",
                mensagens,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 3. Captura erros de validação disparados na persistência direta do JPA/Hibernate
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResposta> tratarErroRestricaoBanco(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        String mensagens = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(" | "));

        ErroResposta erro = new ErroResposta(
                obterTimestampBrasilia(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Consistência de Dados",
                mensagens,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
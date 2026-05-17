package com.tavernhub.exception;

public class ObjetoNaoEncontradoException extends RuntimeException {
    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
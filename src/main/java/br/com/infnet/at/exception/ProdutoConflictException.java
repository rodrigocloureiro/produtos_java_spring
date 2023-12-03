package br.com.infnet.at.exception;

public class ProdutoConflictException extends RuntimeException {
    public ProdutoConflictException() {
    }

    public ProdutoConflictException(String message) {
        super(message);
    }
}

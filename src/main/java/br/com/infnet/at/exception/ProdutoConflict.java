package br.com.infnet.at.exception;

public class ProdutoConflict extends RuntimeException {
    public ProdutoConflict() {
    }

    public ProdutoConflict(String message) {
        super(message);
    }
}

package br.com.infnet.at.exception;

public class ProdutoNotFound extends RuntimeException {
    public ProdutoNotFound() {
    }

    public ProdutoNotFound(String message) {
        super(message);
    }
}

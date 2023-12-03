package br.com.infnet.at.exception;

public class CurrencyConversionException extends RuntimeException {
    public CurrencyConversionException() {
    }

    public CurrencyConversionException(String message) {
        super(message);
    }
}

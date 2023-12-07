package br.com.infnet.at.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayload {
    private String message;
    private List<Produto> products;
    private LocalDateTime dateTime;

    public ResponsePayload(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public ResponsePayload(String message, List<Produto> products) {
        this.message = message;
        this.products = products;
        this.dateTime = LocalDateTime.now();
    }
}

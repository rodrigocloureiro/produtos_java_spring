package br.com.infnet.at.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Produto {
    private int id;
    private String nome;
    private double preco;
    private double precoDolar;
    private ArrayList<String> tamanhos;
}

package br.com.infnet.at.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Produto {
    private int id;
    private String nome;
    private double preco;
    private ArrayList<String> tamanhos;
}

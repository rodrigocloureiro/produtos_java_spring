package br.com.infnet.at.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadCotacao {
//    @JsonProperty("BRLUSD")
    @JsonAlias({"BRLUSD", "BRLEUR"})
    private Cotacao cotacao;
}

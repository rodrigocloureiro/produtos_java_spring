package br.com.infnet.at.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotacaoPayload {
//    @JsonProperty("BRLUSD")
    @JsonAlias({"BRLUSD", "BRLEUR"})
    private Cotacao cotacao;
}

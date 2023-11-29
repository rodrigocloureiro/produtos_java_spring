package br.com.infnet.at.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class Cotacao {
    private String code;
    private String codein;
    private String name;
    private double high;
    private double low;
    private double varBid;
    private double pctChange;
    private double bid;
    private double ask;
    private long timestamp;
    @JsonProperty("create_date")
    private String createDate;
}

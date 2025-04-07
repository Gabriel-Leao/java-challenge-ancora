package br.com.realtech.ancora.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private int id;

    @JsonProperty("marca")
    private String brand;

    @JsonProperty("nomeProduto")
    private String productName;

    @JsonProperty("informacoesComplementares")
    private String additionalInfo;

    @JsonProperty("pontoCriticoAtencao")
    private String criticalAttentionPoint;

    @JsonProperty("dimensoes")
    private String dimensions;

    private Double price = 300.00;

    public Product(int id,String brand, String productName, String additionalInfo, String criticalAttentionPoint, String dimensions) {
        this.id = id;
        this.brand = brand;
        this.productName = productName;
        this.additionalInfo = additionalInfo;
        this.criticalAttentionPoint = criticalAttentionPoint;
        this.dimensions = dimensions;
    }

    public int getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(
                "Id=%d, Marca='%s', " + "Peça='%s', Informações Complementares='%s', Ponto critico de atenção='%s', dimensões='%s', preço=%.2f",
                id, brand, productName, additionalInfo, criticalAttentionPoint,
                dimensions, price
        );
    }
}

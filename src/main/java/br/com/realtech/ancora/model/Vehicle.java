package br.com.realtech.ancora.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    @JsonProperty("montadora")
    private String manufacturer;

    @JsonProperty("modelo")
    private String model;

    @JsonProperty("versao")
    private String version;

    @JsonProperty("chassi")
    private String chassis;

    @JsonProperty("motor")
    private String engine;

    @JsonProperty("combustivel")
    private String fuel;

    @JsonProperty("cambio")
    private String transmission;

    @JsonProperty("carroceria")
    private String body;

    @JsonProperty("anoFabricacao")
    private int manufacturingYear;

    @JsonProperty("anoModelo")
    private int modelYear;

    @JsonProperty("linha")
    private String line;

    @JsonProperty("eixos")
    private Integer axles;

    @JsonProperty("geracao")
    private String generation;

    public Vehicle(
            @JsonProperty("montadora") String manufacturer,
            @JsonProperty("modelo") String model,
            @JsonProperty("versao") String version,
            @JsonProperty("chassi") String chassis,
            @JsonProperty("motor") String engine,
            @JsonProperty("combustivel") String fuel,
            @JsonProperty("cambio") String transmission,
            @JsonProperty("carroceria") String body,
            @JsonProperty("anoFabricacao") int manufacturingYear,
            @JsonProperty("anoModelo") int modelYear,
            @JsonProperty("linha") String line,
            @JsonProperty("eixos") Integer axles,
            @JsonProperty("geracao") String generation
    ) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.version = version;
        this.chassis = chassis;
        this.engine = engine;
        this.fuel = fuel;
        this.transmission = transmission;
        this.body = body;
        this.manufacturingYear = manufacturingYear;
        this.modelYear = modelYear;
        this.line = line;
        this.axles = axles;
        this.generation = generation;
    }

    @Override
    public String toString() {
        return String.format(
                "montadora=%s, Modelo=%s, Versão=%s, Chassi=%s, Motor=%s, Combustível=%s, Cambio=%s, Carroceria=%s, " +
                        "Ano de fabricação=%d, Ano do modelo=%d, Linha=%s, Eixos=%s, Geração=%s",
                manufacturer, model, version, chassis, engine, fuel, transmission, body, manufacturingYear, modelYear,
                line, axles, generation
        );
    }
}

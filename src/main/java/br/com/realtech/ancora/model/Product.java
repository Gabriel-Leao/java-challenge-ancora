package br.com.realtech.ancora.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private int id;

    @JsonProperty("dataModificacao")
    private OffsetDateTime lastModified;

    @JsonProperty("csa")
    private String csa;

    @JsonProperty("cna")
    private String cna;

    @JsonProperty("codigoReferencia")
    private String referenceCode;

    @JsonProperty("ean")
    private String ean;

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

    @JsonProperty("imagemReal")
    private String realImage;

    @JsonProperty("imagemIlustrativa")
    private String illustrativeImage;

    @JsonProperty("fabricante")
    private String manufacturer;

    private Double price = 300.00;

//    @JsonProperty("similares")
//    private final List<Product> similarProducts;

    public Product(int id, OffsetDateTime lastModified, String csa, String cna, String referenceCode, String ean, String brand, String productName, String additionalInfo, String criticalAttentionPoint, String dimensions, String realImage, String illustrativeImage, String manufacturer) {
        this.id = id;
        this.lastModified = lastModified;
        this.csa = csa;
        this.cna = cna;
        this.referenceCode = referenceCode;
        this.ean = ean;
        this.brand = brand;
        this.productName = productName;
        this.additionalInfo = additionalInfo;
        this.criticalAttentionPoint = criticalAttentionPoint;
        this.dimensions = dimensions;
        this.realImage = realImage;
        this.illustrativeImage = illustrativeImage;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(OffsetDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getCsa() {
        return csa;
    }

    public void setCsa(String csa) {
        this.csa = csa;
    }

    public String getCna() {
        return cna;
    }

    public void setCna(String cna) {
        this.cna = cna;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCriticalAttentionPoint() {
        return criticalAttentionPoint;
    }

    public void setCriticalAttentionPoint(String criticalAttentionPoint) {
        this.criticalAttentionPoint = criticalAttentionPoint;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getRealImage() {
        return realImage;
    }

    public void setRealImage(String realImage) {
        this.realImage = realImage;
    }

    public String getIllustrativeImage() {
        return illustrativeImage;
    }

    public void setIllustrativeImage(String illustrativeImage) {
        this.illustrativeImage = illustrativeImage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

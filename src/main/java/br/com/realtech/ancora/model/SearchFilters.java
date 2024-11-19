package br.com.realtech.ancora.model;

public class SearchFilters {
    private String licensePlate;
    private String manufacturer;
    private String product;

    public SearchFilters(String licensePlate, String manufacturer, String product) {
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.product = product;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getProduct() {
        return product;
    }
}

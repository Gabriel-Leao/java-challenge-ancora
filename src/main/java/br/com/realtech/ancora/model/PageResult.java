package br.com.realtech.ancora.model;
import java.util.List;
import java.util.stream.Collectors;

public class PageResult {
    private Vehicle vehicle;
    private List<Product> data;

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public List<Product> getData() { return data; }
    public void setData(List<Product> data) { this.data = data; }

    @Override
    public String toString() {
        return String.format(
                "Veículo: %s\nPeças:\n%s",
                vehicle == null ? "null" : vehicle.toString(),
                data == null ? "null" : data.stream()
                        .map(Product::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}

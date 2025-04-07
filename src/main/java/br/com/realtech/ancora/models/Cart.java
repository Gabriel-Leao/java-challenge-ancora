package br.com.realtech.ancora.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                System.out.println("Produto já está no carrinho.");
                return;
            }
        }
        products.add(product);
        System.out.println("Produto adicionado ao carrinho.");
    }

    public void removeProductById(int id) {
        products.removeIf(product -> product.getId() == id);
        System.out.println("Produto removido do carrinho.");
    }

    public void finalizePurchase() {
        double total = products.stream().mapToDouble(Product::getPrice).sum();
        System.out.printf("Valor total: %.2f\n", total);
        System.out.println("Compra finalizada com sucesso!");
        products.clear();
    }

    public List<Product> getProducts() {
        return products;
    }
}

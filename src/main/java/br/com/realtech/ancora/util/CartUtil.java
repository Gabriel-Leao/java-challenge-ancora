package br.com.realtech.ancora.util;

import br.com.realtech.ancora.model.Cart;
import br.com.realtech.ancora.model.PageResult;
import br.com.realtech.ancora.model.Product;

import java.util.List;
import java.util.Scanner;

public class CartUtil {
    public static void displayProductsAndAddToCart(PageResult pageResult) {
        List<Product> products = pageResult.getData();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        if (products.isEmpty()) {
            System.out.println("Nenhum produto foi encontrado.");
            return;
        }
        System.out.println("Produtos encontrados! Confira abaixo:\n");

        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Ver produtos e adicionar ao carrinho");
            System.out.println("2 - Remover produto do carrinho");
            System.out.println("3 - Finalizar compra");
            System.out.println("4 - Ver carrinho");
            System.out.println("0 - Sair");

            System.out.print("Digite a opção desejada: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("\nProdutos disponíveis:");
                    for (int i = 0; i < products.size(); i++) {
                        System.out.printf("%d - %s\n", i, products.get(i).toString());
                    }
                    System.out.print("Digite o índice do produto que deseja adicionar ao carinho: ");
                    try {
                        int index = Integer.parseInt(scanner.nextLine());
                        if (index >= 0 && index < products.size()) {
                            cart.addProduct(products.get(index));
                        } else {
                            System.out.println("Índice inválido. Tente novamente.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Digite um número.");
                    }
                    break;

                case "2":
                    System.out.println("\nProdutos no carrinho:");
                    List<Product> cartProducts = cart.getProducts();
                    if (cartProducts.isEmpty()) {
                        System.out.println("Carrinho está vazio.");
                    } else {
                        for (int i = 0; i < cartProducts.size(); i++) {
                            System.out.printf("%d - %s\n", i, cartProducts.get(i).toString());
                        }
                        System.out.print("Digite o índice do produto que deseja remover: ");
                        try {
                            int index = Integer.parseInt(scanner.nextLine());
                            if (index >= 0 && index < cartProducts.size()) {
                                cart.removeProductById(cartProducts.get(index).getId());
                            } else {
                                System.out.println("Índice inválido. Tente novamente.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Digite um número.");
                        }
                    }
                    break;

                case "3":
                    cart.finalizePurchase();
                    System.out.println("Obrigado pela sua compra! Volte sempre.");
                    running = false;
                    break;

                case "4":
                    System.out.println("\nProdutos no carrinho:");
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Carrinho está vazio.");
                    } else {
                        for (Product product : cart.getProducts()) {
                            System.out.println(product);
                        }
                    }
                    break;

                case "0":
                    System.out.println("Saindo sem finalizar a compra. Obrigado!");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}

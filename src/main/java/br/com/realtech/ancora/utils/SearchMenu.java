package br.com.realtech.ancora.utils;

import br.com.realtech.ancora.dtos.SearchFilters;

import java.util.Scanner;

public class SearchMenu {

    public SearchFilters displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String licensePlate = "";
        String manufacturer = "";
        String product = "";
        boolean validOption = false;

        while (!validOption) {
            System.out.println("Selecione a opção de busca:");
            System.out.println("1 - Padrão (Placa, Peça, Fabricante)");
            System.out.println("2 - Buscar por Placa");
            System.out.println("3 - Buscar por Peça");
            System.out.println("4 - Buscar por Fabricante");
            System.out.print("=> ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Digite a placa do veículo: ");
                    licensePlate = scanner.nextLine();
                    System.out.print("Digite o nome da peça: ");
                    product = scanner.nextLine();
                    System.out.print("Digite o fabricante da peça: ");
                    manufacturer = scanner.nextLine();
                    validOption = true;
                    break;
                case "2":
                    System.out.print("Digite a placa do veículo: ");
                    licensePlate = scanner.nextLine();
                    validOption = true;
                    break;
                case "3":
                    System.out.print("Digite o nome da peça: ");
                    product = scanner.nextLine();
                    validOption = true;
                    break;
                case "4":
                    System.out.print("Digite o fabricante da peça: ");
                    manufacturer = scanner.nextLine();
                    validOption = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        return new SearchFilters(licensePlate, manufacturer, product);
    }
}

package aula1.exercicio1;

import aula1.exercicio1.model.Produto;
import aula1.exercicio1.service.ProductService;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductService productService = new ProductService();

    public static void main(String[] args) {
        int option = 0;

        while (option != 3) {
            showMenu();
            option = readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1 -> buscarProdutos();
                    case 2 -> listarTodos();
                    case 3 -> System.out.println("Encerrando Programa ...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                // Tratamento de erros exigido na Parte 5 (API fora, etc)
                System.out.println("Erro na operação: " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
                =========================================
                DUMMY JSON PRODUCTS APP - AULA 1
                =========================================
                1 - Buscar produtos
                2 - Listar produtos
                3 - Sair
                =========================================
                """);
    }

    private static void listarTodos() throws Exception {
        System.out.println("\nBuscando todos os produtos...");
        List<Produto> produtos = productService.listarProdutos();
        exibirLista(produtos);
    }

    private static void buscarProdutos() throws Exception {
        // Parte 4: Entrada dinâmica de busca
        System.out.print("Digite o produto para busca (ex: phone): ");
        String termo = scanner.nextLine();

        if (termo.isBlank()) {
            System.out.println("Termo de busca não pode ser vazio.");
            return;
        }

        List<Produto> produtos = productService.buscarProdutos(termo);
        exibirLista(produtos);
    }

    private static void exibirLista(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        System.out.println("\nProdutos encontrados:");
        System.out.println("-----------------------------------------");
        // Parte 2: Exibir apenas título, preço e categoria formatados de forma amigável
        for (Produto p : produtos) {
            System.out.println("Produto:   " + p.getTitulo());
            System.out.println("Preço:     $ " + p.getPreco());
            System.out.println("Categoria: " + p.getCategoria());
            System.out.println("-----------------------------------------");
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
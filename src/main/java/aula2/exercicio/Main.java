package aula2.exercicio;

import aula2.exercicio.model.Post;
import aula2.exercicio.model.User;
import aula2.exercicio.service.PostService;
import aula2.exercicio.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {

    // Scanner usado para ler as entradas digitadas pelo usuário.
    private static final Scanner scanner = new Scanner(System.in);

    // Services responsáveis pelas regras e integrações com a API.
    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();

    public static void main(String[] args) {
        // Variável que controla a opção escolhida no menu.
        int option = 0;

        // Enquanto o usuário não escolher sair, o menu continua aparecendo.
        while (option != 4) {
            showMenu();
            option = readInt("Escolha uma opção: ");

            try {
                // switch usado para direcionar a opção escolhida para o método correto.
                switch (option) {
                    case 1 -> listUsers();
                    case 2 -> searchUsersId();
                    case 3 -> listPosts();
                    case 4 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                // Tratamento simples para evitar que o sistema quebre no console.
                System.out.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // Método responsável por exibir o menu principal.
    private static void showMenu() {
        System.out.println("""
                =========================================
                JSONPlaceholder CONSOLE APP
                =========================================
                1 - Listar usuários
                2 - Buscar usuário por ID
                3 - Listar posts de usuário
                4 - Sair
                =========================================
                """);
    }

    // Lista usuários da API utilizando limit e skip com formatação empilhada.
    private static void listUsers() throws Exception {
        int limit = readInt("Digite o limit: ");
        int skip = readInt("Digite o skip: ");

        // Chama o service que conversa com a API.
        List<User> users = userService.list(limit, skip);

        System.out.println("\n=========================================");
        System.out.println("Usuários encontrados:");
        System.out.println("=========================================");

        // Percorre a lista e imprime de forma empilhada conforme o PDF (Pág. 32)
        for (User user : users) {
            System.out.println("ID:       " + user.getId());
            System.out.println("Nome:     " + user.getName()); // Atualizado para getName() conforme o exercício
            System.out.println("Email:    " + user.getEmail());
            System.out.println("Telefone: " + user.getPhone());
            System.out.println("Website:  " + user.getWebsite());
            System.out.println("-----------------------------------------");
        }
    }

    // Busca usuário por termo com texto digitado e formatação empilhada.
    private static void searchUsersId() throws Exception {
        String term = readText("Digite o nome ou termo para buscar o usuário: ");

        // Chama o service para buscar na API.
        List<User> users = userService.search(term);

        if (users.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("Usuários encontrados:");
        System.out.println("=========================================");

        // Exibe os usuários encontrados de forma empilhada conforme o PDF (Pág. 32)
        for (User user : users) {
            System.out.println("ID:       " + user.getId());
            System.out.println("Nome:     " + user.getName());
            System.out.println("Email:    " + user.getEmail());
            System.out.println("Telefone: " + user.getPhone());
            System.out.println("Website:  " + user.getWebsite());

            // === BLOCO DO DESAFIO EXTRA ===
            try {
                int qtdPosts = postService.contarPostsDoUsuario(user.getId());
                System.out.println("Quantidade de posts: " + qtdPosts);
            } catch (Exception e) {
                System.out.println("Quantidade de posts: Erro ao calcular");
            }

            System.out.println("-----------------------------------------");
        }
    }

    // Lista publicações da API usando paginação.
    private static void listPosts() throws Exception {
        int limit = readInt("Digite o limit: ");
        int skip = readInt("Digite o skip: ");

        // Chama o service de posts.
        List<Post> posts = postService.list(limit, skip);

        System.out.println("\n=========================================");
        System.out.println("Posts encontrados:");
        System.out.println("=========================================");

        // Percorre as publicações e exibe conforme a exigência do PDF (Pág. 32)
        for (Post post : posts) {
            System.out.printf("User ID: %d | POST ID: %d%n", post.getUserId(), post.getId());
            System.out.printf("Título:  %s%n", post.getTitle());
            System.out.printf("Corpo:   %s%n", post.getBody());
            System.out.println("-----------------------------------------");
        }
    }

    // Métodos auxiliares de leitura e validação com Scanner
    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine());
                if (value < 0) {
                    System.out.println("Digite um número maior ou igual a zero.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine();
            if (value == null || value.isBlank()) {
                System.out.println("Texto não pode ser vazio.");
                continue;
            }
            return value;
        }
    }

    private static boolean readBoolean(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine();
            if (value.equalsIgnoreCase("true")) return true;
            if (value.equalsIgnoreCase("false")) return false;
            System.out.println("Digite apenas true ou false.");
        }
    }
}
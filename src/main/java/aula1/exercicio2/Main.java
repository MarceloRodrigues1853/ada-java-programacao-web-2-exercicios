package aula1.exercicio2;

import aula1.exercicio2.model.Endereco;
import aula1.exercicio2.model.CepHistory;
import aula1.exercicio2.service.CepService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CepService cepService = new CepService();

    public static void main(String[] args) {
        int option = 0;

        // Desafio Extra 2: Consulta infinita sem reiniciar o programa (Loop while)
        while (option != 3) {
            showMenu();
            option = readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1 -> iniciarConsulta();
                    case 2 -> exibirHistorico();
                    case 3 -> System.out.println("Encerrando o sistema de CEP. Até logo!");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                // Parte 6: Exibe mensagens amigáveis em caso de erro
                System.out.println("\n[ERRO] " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        // Desafio Extra 3: Títulos e separadores visuais amigáveis
        System.out.println("""
                
                =========================================
                SISTEMA DE CONSULTA DE CEP - VIA CEP
                =========================================
                1 - Consultar CEP
                2 - Ver histórico de CEPs
                3 - Sair
                =========================================
                """);
    }

    private static void iniciarConsulta() throws Exception {
        System.out.print("Digite o CEP (apenas números, 8 dígitos): ");
        String cepInput = scanner.nextLine().trim();

        // Parte 6 e Desafio Extra 4: Valida se contém apenas números e tem o tamanho correto (8 dígitos)
        if (!cepInput.matches("\\d{8}")) {
            throw new RuntimeException("CEP inválido! Deve conter exatamente 8 caracteres numéricos.");
        }

        System.out.println("Buscando endereço...");
        Endereco end = cepService.consultarCep(cepInput);

        // Parte 4: Exibição limpa (Não exibe o JSON puro bruto)
        System.out.println("\n=========================================");
        System.out.println("Endereço Encontrado:");
        System.out.println("=========================================");
        System.out.println("CEP:         " + end.getCep());
        System.out.println("Logradouro:  " + end.getLogradouro());
        System.out.println("Bairro:      " + end.getBairro());
        System.out.println("Cidade:      " + end.getLocalidade());
        System.out.println("Estado:      " + end.getUf());
        System.out.println("-----------------------------------------");
    }

    private static void exibirHistorico() {
        List<String> historico = CepHistory.listarTodos();

        System.out.println("\n=========================================");
        System.out.println("CEPs Pesquisados no Histórico:");
        System.out.println("=========================================");

        if (historico.isEmpty()) {
            System.out.println("Nenhum CEP foi pesquisado nesta sessão.");
        } else {
            for (String cep : historico) {
                System.out.println("• " + cep);
            }
        }
        System.out.println("-----------------------------------------");
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }
}
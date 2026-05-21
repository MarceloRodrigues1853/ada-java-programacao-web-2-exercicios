package aula2.exercicio2.br.com.ada;

import aula2.exercicio2.br.com.ada.model.Weather;
import aula2.exercicio2.br.com.ada.model.WeatherHistory;
import aula2.exercicio2.br.com.ada.service.WeatherService;

import java.util.List;
import java.util.Scanner;

public class Main {
    // Scanner usado para ler as entradas digitadas pelo usuário.
    private static final Scanner scanner = new Scanner(System.in);

    // Instância do Service responsável pela integração com a API wttr.in
    private static final WeatherService weatherService = new WeatherService();

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
                    case 1 -> consultarClima();
                    case 2 -> verHistorico();
                    case 3 -> limparHistorico();
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
                CONSULTA CLIMÁTICA wttr.in CONSOLE APP
                =========================================
                1 - Consultar clima
                2 - Ver histórico
                3 - Limpar histórico
                4 - Sair
                =========================================
                """);
    }

    // Opção 1: Lê o nome da cidade, faz a requisição na API e exibe o resultado
    private static void consultarClima() throws Exception {
        String cidadeInput = readText("Digite o nome da cidade para consultar o clima: ");

        System.out.println("\nBuscando informações meteorológicas...");
        Weather weather = weatherService.consultarClima(cidadeInput);

        System.out.println("\n=========================================");
        System.out.println("Condições Climáticas Atuais:");
        System.out.println("=========================================");
        imprimirDadosClima(weather);
    }

    // Opção 2: Recupera a lista guardada no WeatherHistory e imprime de forma empilhada
    private static void verHistorico() {
        List<Weather> historico = WeatherHistory.listarTodos();

        if (historico.isEmpty()) {
            System.out.println("\nO histórico de consultas está vazio.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("Histórico de Consultas:");
        System.out.println("=========================================");

        for (Weather weather : historico) {
            imprimirDadosClima(weather);
        }
    }

    // Opção 3: Esvazia a lista estática na memória
    private static void limparHistorico() {
        WeatherHistory.limpar(); // Ou .limpar() dependendo do nome que deixou na classe
        System.out.println("\nHistórico de consultas deletado com sucesso.");
    }

    // Método auxiliar para manter a formatação visual idêntica para a consulta e o histórico
    private static void imprimirDadosClima(Weather weather) {
        System.out.println("Cidade:      " + weather.getCidade());
        System.out.println("Temperatura: " + weather.getTemperatura() + "°C");
        System.out.println("Descrição:   " + weather.getDescricao());
        System.out.println("Umidade:     " + weather.getUmidade() + "%");
        System.out.println("-----------------------------------------");
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
}
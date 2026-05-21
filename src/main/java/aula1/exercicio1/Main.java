package aula1.exercicio1;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Scanner para capturar o que o usuário digita no console
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== SISTEMA DE BUSCA DE PRODUTOS ===");
        System.out.print("Digite o termo que deseja pesquisar (ex: phone, mascara): ");
        String termoBusca = teclado.nextLine();

        try {
            // Parte 4: Montagem da URL dinâmica de pesquisa conforme o PDF
            String enderecoApi = "https://dummyjson.com/products/search?q=" + termoBusca;
            URL url = new URL(enderecoApi);

            // Abre a conexão
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            // Parte 5: Tratamento de erro de conexão baseado no Status Code
            int statusCode = conexao.getResponseCode();

            if (statusCode == 200) {
                // Conecta o leitor à resposta da internet
                Scanner leitorRede = new Scanner(conexao.getInputStream());
                StringBuilder jsonBruto = new StringBuilder();

                while (leitorRede.hasNext()) {
                    jsonBruto.append(leitorRede.nextLine());
                }
                leitorRede.close();

                System.out.println("\n--- Resultado da Busca para '" + termoBusca + "' ---");
                System.out.println(jsonBruto.toString());

            } else {
                // Trata erros retornados pela própria API (ex: endpoint inválido ou erro interno)
                System.out.println("Erro da API! O servidor respondeu com código HTTP: " + statusCode);
            }

        } catch (java.net.UnknownHostException e) {
            // Parte 5: Captura especificamente o erro de "internet fora do ar"
            System.out.println("Erro: Não foi possível conectar à internet. Verifique sua rede.");
        } catch (Exception e) {
            // Captura qualquer outra falha genérica de código
            System.out.println("Ocorreu uma falha inesperada: " + e.getMessage());
        } finally {
            teclado.close(); // Fecha o recurso do teclado de forma segura
        }
    }
}
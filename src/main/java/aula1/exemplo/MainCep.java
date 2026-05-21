package aula1.exemplo;

// Classe nativa do Java para criar servidor HTTP
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;

// Classes responsáveis pela conexão HTTP
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

// Scanner utilizado para ler resposta da API externa
import java.util.Scanner;

public class MainCep {

    public static void main(String[] args) throws Exception {

        // Cria servidor HTTP na porta 8080
        HttpServer servidor =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );

        // Cria endpoint:
        // http://localhost:8080/cep
        servidor.createContext("/cep", exchange -> {

            // CEP fixo
            String cep = "01001000";

            // Monta URL da API ViaCEP
            String enderecoApi =
                    "https://viacep.com.br/ws/"
                            + cep +
                            "/json/";

            // Cria objeto URL da API externa
            URL url = new URL(enderecoApi);

            // Abre conexão HTTP com ViaCEP
            HttpURLConnection conexao =
                    (HttpURLConnection) url.openConnection();

            // Define método HTTP GET
            conexao.setRequestMethod("GET");

            // Scanner utilizado para ler resposta da API
            Scanner scanner =
                    new Scanner(conexao.getInputStream());

            // Variável para armazenar JSON completo
            StringBuilder respostaApi =
                    new StringBuilder();

            // Enquanto houver conteúdo na resposta
            while (scanner.hasNext()) {

                // Adiciona conteúdo na variável respostaApi
                respostaApi.append(scanner.nextLine());
            }

            // Fecha scanner
            scanner.close();

            // Define tipo da resposta HTTP
            exchange.getResponseHeaders()
                    .add("Content-Type", "application/json");

            // Define status HTTP 200
            exchange.sendResponseHeaders(
                    200,
                    respostaApi.toString().getBytes().length
            );

            // Obtém corpo da resposta HTTP
            OutputStream output =
                    exchange.getResponseBody();

            // Escreve JSON retornado pela ViaCEP
            output.write(
                    respostaApi.toString().getBytes()
            );

            // Finaliza resposta
            output.close();
        });

        // Inicia servidor
        servidor.start();

        System.out.println(
                "Servidor rodando em http://localhost:8080/cep"
        );
    }
}

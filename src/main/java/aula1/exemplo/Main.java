package aula1.exemplo;

// Responsável por converter objeto Java em JSON
import com.fasterxml.jackson.databind.ObjectMapper;

// Classe nativa do Java para criar servidor HTTP
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        // Cria servidor HTTP na porta 8080
        HttpServer servidor =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );

        // Cria endpoint:
        // http://localhost:8080/pessoa
        servidor.createContext("/pessoa", exchange -> {

            // Criando objeto Java
            Pessoa pessoa =
                    new Pessoa(
                            "Stephannie",
                            "Programação Web"
                    );

            // Classe responsável pela conversão JSON
            ObjectMapper mapper = new ObjectMapper();

            // Converte objeto Pessoa para JSON
            String resposta =
                    mapper.writeValueAsString(pessoa);

            // Define tipo da resposta HTTP
            // Informamos que estamos devolvendo JSON
            exchange.getResponseHeaders()
                    .add("Content-Type", "application/json");

            // Define status HTTP 200 (sucesso)
            // Também informa tamanho da resposta
            exchange.sendResponseHeaders(
                    200,
                    resposta.getBytes().length
            );

            // Obtém corpo da resposta HTTP
            OutputStream output =
                    exchange.getResponseBody();

            // Escreve JSON na resposta
            output.write(resposta.getBytes());

            // Finaliza resposta
            output.close();
        });

        // Inicia servidor
        servidor.start();

        System.out.println(
                "Servidor rodando em http://localhost:8080/pessoa"
        );
    }
}
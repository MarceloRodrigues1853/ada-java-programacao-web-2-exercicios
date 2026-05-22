package aula1.exercicio1.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

// Classe utilitária responsável por concentrar as chamadas HTTP.
// A ideia é evitar repetir código de conexão em ProductService e TodoService.
public class HttpClientUtil {

    // Método para requisições GET.
    // Usado quando queremos buscar informações na API.
    public String get(String urlApi) throws Exception {
        return execute(urlApi, "GET", null);
    }

    // Método para requisições POST.
    // Usado quando queremos criar uma nova informação na API.
    public String post(String urlApi, String jsonBody) throws Exception {
        return execute(urlApi, "POST", jsonBody);
    }

    // Método para requisições PUT.
    // Usado quando queremos atualizar uma informação existente na API.
    public String put(String urlApi, String jsonBody) throws Exception {
        return execute(urlApi, "PUT", jsonBody);
    }

    // Método para requisições DELETE.
    // Usado quando queremos remover uma informação na API.
    public String delete(String urlApi) throws Exception {
        return execute(urlApi, "DELETE", null);
    }

    // Método central que executa a chamada HTTP.
    // Ele recebe a URL, o método HTTP e, quando necessário, o corpo JSON da requisição.
    private String execute(String urlApi, String method, String jsonBody) throws Exception {

        // Cria o objeto URL com o endereço da API.
        URL url = new URL(urlApi);

        // Abre a conexão HTTP com a API externa.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Define qual método HTTP será usado: GET, POST, PUT ou DELETE.
        conn.setRequestMethod(method);

        // Informa para a API que estamos enviando JSON.
        conn.setRequestProperty("Content-Type", "application/json");

        // Informa para a API que esperamos receber JSON como resposta.
        conn.setRequestProperty("Accept", "application/json");

        // Se existir corpo na requisição, significa que precisamos enviar dados para a API.
        // Isso acontece principalmente em POST e PUT.
        if (jsonBody != null && !jsonBody.isBlank()) {

            // Habilita envio de dados no corpo da requisição.
            conn.setDoOutput(true);

            // Escreve o JSON no corpo da requisição.
            try (OutputStream output = conn.getOutputStream()) {
                output.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            }
        }

        // Captura o status HTTP retornado pela API.
        // Exemplos: 200, 201, 404, 500.
        int status = conn.getResponseCode();

        Scanner scanner;

        // Se o status for 2xx, lemos a resposta normal.
        if (status >= 200 && status < 300) {
            scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
        } else {
            // Se der erro, lemos o conteúdo de erro retornado pela API.
            scanner = new Scanner(conn.getErrorStream(), StandardCharsets.UTF_8);
        }

        // StringBuilder será usado para montar a resposta completa da API.
        StringBuilder response = new StringBuilder();

        // Enquanto houver dados na resposta, vamos lendo e juntando.
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }

        scanner.close();

        // Se o status não for sucesso, lançamos uma exceção com mensagem amigável.
        if (status < 200 || status >= 300) {
            throw new RuntimeException("Erro HTTP " + status + ": " + response);
        }

        // Retorna o JSON como String.
        return response.toString();
    }
}

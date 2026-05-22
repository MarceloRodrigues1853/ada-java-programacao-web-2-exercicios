package aula1.exercicio2.service;

import aula1.exercicio2.model.Endereco;
import aula1.exercicio2.model.CepHistory;
import aula2.exercicio2.br.com.ada.http.HttpClientUtil; // Reaproveitando client HTTP
import com.fasterxml.jackson.databind.ObjectMapper;

public class CepService {

    private static final String BASE_URL = "https://viacep.com.br/ws/%s/json/";
    private final HttpClientUtil httpClient = new HttpClientUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    public Endereco consultarCep(String cep) throws Exception {
        // Monta a URL dinâmica injetando o CEP limpo
        String url = String.format(BASE_URL, cep.trim().replace("-", ""));

        // Faz o consumo HTTP da API ViaCEP
        String json = httpClient.get(url);

        // A API do ViaCEP retorna um JSON contendo {"erro": "true"} se o CEP não existir
        if (json.contains("\"erro\":")) {
            throw new RuntimeException("CEP inexistente na base de dados do ViaCEP.");
        }

        // Transforma o JSON diretamente no nosso objeto Endereco
        Endereco endereco = mapper.readValue(json, Endereco.class);

        // Desafio Extra 1: Salva o CEP formatado no histórico após o sucesso
        CepHistory.adicionar(endereco.getCep());

        return endereco;
    }
}
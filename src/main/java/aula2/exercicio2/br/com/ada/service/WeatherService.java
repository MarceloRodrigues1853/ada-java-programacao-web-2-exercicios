package aula2.exercicio2.br.com.ada.service;

import aula2.exercicio2.br.com.ada.dto.WeatherResponse;
import aula2.exercicio2.br.com.ada.http.HttpClientUtil;
import aula2.exercicio2.br.com.ada.model.Weather;
import aula2.exercicio2.br.com.ada.model.WeatherHistory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherService {

    // URL base da API wttr.in configurada para retornar JSON estruturado (?format=j1)
    private static final String BASE_URL = "https://wttr.in/%s?format=j1";
    private final HttpClientUtil httpClient = new HttpClientUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    public Weather consultarClima(String cidadePesquisada) throws Exception {
        // Substitui o %s na URL pelo nome da cidade (ex: wttr.in/London?format=j1)
        // Substituímos espaços por + para evitar quebras na URL
        String cidadeUrl = cidadePesquisada.trim().replace(" ", "+");
        String url = String.format(BASE_URL, cidadeUrl);

        // Faz a requisição HTTP e captura a resposta bruta em texto JSON
        String json = httpClient.get(url);

        // Desserializa o JSON usando a nossa árvore de DTOs
        WeatherResponse response = mapper.readValue(json, WeatherResponse.class);

        // Validação de segurança: caso a API retorne estruturas vazias
        if (response.getCurrentCondition() == null || response.getCurrentCondition().isEmpty() ||
                response.getNearestArea() == null || response.getNearestArea().isEmpty()) {
            throw new RuntimeException("Não foi possível obter dados climáticos para esta localização.");
        }

        // Navega pelas camadas do DTO para extrair as variáveis primitivas
        // 1. Extrai os dados climáticos atuais
        var condicaoAtual = response.getCurrentCondition().get(0);
        double temperatura = condicaoAtual.getTempC();
        double umidade = condicaoAtual.getHumidity();

        // 2. Extrai a descrição textual (ex: "Sunny") se ela existir
        String descricao = "Não informada";
        if (condicaoAtual.getWeatherDesc() != null && !condicaoAtual.getWeatherDesc().isEmpty()) {
            descricao = condicaoAtual.getWeatherDesc().get(0).getValue();
        }

        // 3. Extrai o nome oficial da cidade retornado pela API
        String cidadeEncontrada = cidadePesquisada;
        var area = response.getNearestArea().get(0);
        if (area.getAreaName() != null && !area.getAreaName().isEmpty()) {
            cidadeEncontrada = area.getAreaName().get(0).getValue();
        }

        // Cria o nosso objeto de domínio (Model) com os dados limpos
        Weather weather = new Weather(cidadeEncontrada, temperatura, descricao, umidade);

        // Salva automaticamente esta consulta bem-sucedida dentro do Histórico em memória
        WeatherHistory.adicionar(weather);

        return weather;
    }
}
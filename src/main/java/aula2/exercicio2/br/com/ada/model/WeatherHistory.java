package aula2.exercicio2.br.com.ada.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherHistory {
    // Lista estática que vai reter o histórico em memória enquanto o app estiver rodando
    private static final List<Weather> historico = new ArrayList<>();

    public static void adicionar(Weather weather) {
        historico.add(weather);
    }

    public static List<Weather> listarTodos() {
        return new ArrayList<>(historico); // Retorna uma cópia para proteger a lista original
    }

    public static void limpar() {
        historico.clear();
    }
}
package aula1.exercicio2.model;

import java.util.ArrayList;
import java.util.List;

public class CepHistory {
    private static final List<String> cepsPesquisados = new ArrayList<>();

    public static void adicionar(String cep) {
        if (!cepsPesquisados.contains(cep)) {
            cepsPesquisados.add(cep);
        }
    }

    public static List<String> listarTodos() {
        return new ArrayList<>(cepsPesquisados);
    }
}
package aula2.exercicio2.br.com.ada.model;

import lombok.Data;

@Data
public class Weather {
    private String cidade;
    private double temperatura;
    private String descricao;
    private double umidade;

    public Weather(String cidade, double temperatura, String descricao, double umidade) {
        this.cidade = cidade;
        this.temperatura = temperatura;
        this.descricao = descricao;
        this.umidade = umidade;
    }
}

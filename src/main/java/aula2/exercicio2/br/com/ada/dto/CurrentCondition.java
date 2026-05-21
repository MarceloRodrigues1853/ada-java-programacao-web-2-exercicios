package aula2.exercicio2.br.com.ada.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentCondition {
    @JsonProperty("temp_C")
    private double tempC; // Temperatura em Celsius

    private double humidity; // Umidade

    @JsonProperty("weatherDesc")
    private List<WeatherDescription> weatherDesc; // Lista com a descrição textual
}
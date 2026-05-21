package aula2.exercicio2.br.com.ada.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("current_condition")
    private List<CurrentCondition> currentCondition;

    @JsonProperty("nearest_area")
    private List<NearestArea> nearestArea;
}

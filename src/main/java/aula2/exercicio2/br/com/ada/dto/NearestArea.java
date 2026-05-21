package aula2.exercicio2.br.com.ada.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearestArea {
    @JsonProperty("areaName")
    private List<AreaName> areaName;
}
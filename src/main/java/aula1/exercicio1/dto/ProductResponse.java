package aula1.exercicio1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    // Captura o array "products" do JSON da API
    private List<ProductDto> products;
}

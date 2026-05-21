package aula2.exercicio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    // Atributos com os nomes exatos que vêm no JSON da API externa
    private int id;
    private String name;
    private String email;
    private String phone;
    private String website;
}
package aula1.exercicio2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Evita quebras caso a API envie campos novos
public class Endereco {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade; // Representa a Cidade no JSON da API
    private String uf;         // Representa o Estado no JSON da API
}
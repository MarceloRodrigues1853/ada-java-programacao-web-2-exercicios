package aula2.exercicio.dto;

import lombok.Data;

@Data
public class PostDto {
    // Atributos com os nomes exatos que vêm no JSON do JSONPlaceholder
    private int userId;
    private int id;
    private String title;
    private String body;
}
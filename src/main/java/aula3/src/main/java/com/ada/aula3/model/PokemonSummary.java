package com.ada.aula3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List; // <-- IMPORTANTE: Importar o List

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonSummary {
    private int id;
    private String name;
    private int height;
    private int weight;
    private List<String> types; // <-- AJUSTADO: Mudou de String para List<String>
    private String mainAbility;
    private int baseExperience;
    private String color;
    private String habitat;
    private String generation;

    // Removendo o prefixo "is" apenas aqui na variável para o Lombok gerar os Setters perfeitos
    private Boolean baby;
    private Boolean legendary;
    private Boolean mythical;
}
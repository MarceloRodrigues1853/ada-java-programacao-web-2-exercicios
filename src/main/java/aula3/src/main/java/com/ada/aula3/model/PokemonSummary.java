package com.ada.aula3.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonSummary {
    private int id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotNull(message = "A altura não pode ser nula")
    @Positive(message = "A altura deve ser positiva")
    private int height;

    @NotNull(message = "O peso não pode ser nulo")
    @Positive(message = "O peso deve ser positivo")
    private int weight;

    private List<String> types;
    private String mainAbility;
    private int baseExperience;
    private String color;
    private String habitat;
    private String generation;

    private Boolean baby;
    private Boolean legendary;
    private Boolean mythical;
}
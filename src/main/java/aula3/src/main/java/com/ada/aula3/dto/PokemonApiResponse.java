package com.ada.aula3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonApiResponse {
    private int id;
    private String name;
    private int height;
    private int weight;

    @JsonProperty("base_experience")
    private int baseExperience;

    private List<PokemonTypeDto> types;
    private List<PokemonAbilityDto> abilities;
}
package com.ada.aula3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonAbilityDto {
    private NamedResourceDto ability;

    @JsonProperty("is_hidden")
    private boolean isHidden;
}
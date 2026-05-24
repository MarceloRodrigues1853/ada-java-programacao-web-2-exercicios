package com.ada.aula3.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpeciesResponse {
    private NamedResourceDto color;
    private NamedResourceDto habitat;
    private NamedResourceDto generation;

    @JsonProperty("is_baby")
    private boolean isBaby;

    @JsonProperty("is_legendary")
    private boolean isLegendary;

    @JsonProperty("is_mythical")
    private boolean isMythical;
}
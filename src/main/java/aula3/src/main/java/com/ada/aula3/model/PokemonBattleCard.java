package com.ada.aula3.model;

import lombok.Data;

/**
 * Model que representa a versão compacta e visual do Pokémon,
 * projetada especificamente para o endpoint de cartas de batalha.
 */
@Data
public class PokemonBattleCard {
    // Exemplo esperado: "PIKACHU generation-i"
    private String title;

    // Classificação baseada em flags (ex: "Legendary Pokémon", "Common Pokémon")
    private String classification;

    // O tipo elemental principal do Pokémon
    private String mainType;

    // A habilidade padrão que não está oculta
    private String mainAbility;

    // Informações físicas combinadas (ex: "Height: 4 | Weight: 60")
    private String physicalInfo;
}
package com.ada.aula3.service;

import com.ada.aula3.dto.PokemonApiResponse;
import com.ada.aula3.dto.PokemonSpeciesResponse;
import com.ada.aula3.model.PokemonBattleCard;
import com.ada.aula3.model.PokemonSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PokemonSummary obterResumoPokemon(String nameOrId) {
        String busca = nameOrId.toLowerCase().trim();

        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/" + busca;
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/" + busca;

        PokemonApiResponse pokeApi = restTemplate.getForObject(urlPokemon, PokemonApiResponse.class);
        PokemonSpeciesResponse speciesApi = restTemplate.getForObject(urlSpecies, PokemonSpeciesResponse.class);

        if (pokeApi == null || speciesApi == null) {
            throw new RuntimeException("Dados do Pokémon não encontrados na PokeAPI.");
        }

        List<String> tipos = pokeApi.getTypes().stream()
                .map(t -> t.getType().getName())
                .collect(Collectors.toList());

        String principalHabilidade = pokeApi.getAbilities().stream()
                .filter(a -> !a.isHidden())
                .map(a -> a.getAbility().getName())
                .findFirst()
                .orElse("Nenhuma");

        PokemonSummary summary = new PokemonSummary();
        summary.setId(pokeApi.getId());
        summary.setName(pokeApi.getName());
        summary.setHeight(pokeApi.getHeight());
        summary.setWeight(pokeApi.getWeight());
        summary.setTypes(tipos);
        summary.setMainAbility(principalHabilidade);
        summary.setBaseExperience(pokeApi.getBaseExperience());

        summary.setColor(speciesApi.getColor() != null ? speciesApi.getColor().getName() : "Desconhecido");
        summary.setHabitat(speciesApi.getHabitat() != null ? speciesApi.getHabitat().getName() : "Desconhecido");
        summary.setGeneration(speciesApi.getGeneration() != null ? speciesApi.getGeneration().getName() : "Desconhecida");
        summary.setBaby(speciesApi.isBaby());
        summary.setLegendary(speciesApi.isLegendary());
        summary.setMythical(speciesApi.isMythical());

        return summary;
    }

    /**
     * Método do Desafio Extra: Monta a estrutura visual do Battle Card
     * reaproveitando a mesma lógica de consumo das duas APIs.
     */
    public PokemonBattleCard obterCardBatalha(String nameOrId) {
        String busca = nameOrId.toLowerCase().trim();

        // URLs de consulta dos dois endpoints externos
        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/" + busca;
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/" + busca;

        // Dispara o cliente HTTP e desencaixota as respostas nos DTOs mapeados
        PokemonApiResponse pokeApi = restTemplate.getForObject(urlPokemon, PokemonApiResponse.class);
        PokemonSpeciesResponse speciesApi = restTemplate.getForObject(urlSpecies, PokemonSpeciesResponse.class);

        if (pokeApi == null || speciesApi == null) {
            throw new RuntimeException("Não foi possível obter dados para o Battle Card.");
        }

        // Instancia o modelo focado na carta de batalha
        PokemonBattleCard card = new PokemonBattleCard();

        // 1. Monta o TITLE: Nome em maiúsculas + Geração vinda da API da espécie
        String geracao = speciesApi.getGeneration() != null ? speciesApi.getGeneration().getName() : "unknown";
        card.setTitle(pokeApi.getName().toUpperCase() + " " + geracao);

        // 2. Monta a CLASSIFICATION: Verifica as flags booleanas em cascata
        if (speciesApi.isLegendary()) {
            card.setClassification("Legendary Pokémon");
        } else if (speciesApi.isMythical()) {
            card.setClassification("Mythical Pokémon");
        } else if (speciesApi.isBaby()) {
            card.setClassification("Baby Pokémon");
        } else {
            card.setClassification("Common Pokémon"); // Padrão solicitado no slide
        }

        // 3. Monta o MAINTYPE: Captura o primeiro tipo que vier na lista do DTO
        String tipoPrincipal = "unknown";
        if (pokeApi.getTypes() != null && !pokeApi.getTypes().isEmpty()) {
            tipoPrincipal = pokeApi.getTypes().get(0).getType().getName();
        }
        card.setMainType(tipoPrincipal);

        // 4. Monta a MAINABILITY: Filtra habilidades não ocultas e pega a primeira
        String habilidadePrincipal = pokeApi.getAbilities().stream()
                .filter(a -> !a.isHidden())
                .map(a -> a.getAbility().getName())
                .findFirst()
                .orElse("None");
        card.setMainAbility(habilidadePrincipal);

        // 5. Monta o PHYSICALINFO: Concatena a altura e o peso em uma String amigável
        card.setPhysicalInfo("Height: " + pokeApi.getHeight() + " | Weight: " + pokeApi.getWeight());

        return card;
    }

}
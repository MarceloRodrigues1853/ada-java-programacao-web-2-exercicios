package com.ada.aula3.service;

import com.ada.aula3.dto.PokemonApiResponse;
import com.ada.aula3.dto.PokemonSpeciesResponse;
import com.ada.aula3.exception.PokemontNotFoundException;
import com.ada.aula3.model.PokemonBattleCard;
import com.ada.aula3.model.PokemonSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

        // LOG DO DESAFIO BÔNUS: Para monitorar quando a API externa é chamada
        System.out.println("Consultando API externa PokeAPI para o resumo de: " + busca);

        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/" + busca;
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/" + busca;

        try {
            PokemonApiResponse pokeApi = restTemplate.getForObject(urlPokemon, PokemonApiResponse.class);
            PokemonSpeciesResponse speciesApi = restTemplate.getForObject(urlSpecies, PokemonSpeciesResponse.class);

            if (pokeApi == null || speciesApi == null) {
                throw new PokemontNotFoundException("Pokémon não encontrado para o termo: " + nameOrId);
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

        } catch (HttpClientErrorException.NotFound e) {
            // Captura o erro 404 da PokeAPI externa e lança a sua Exception tratada pelo Handler
            throw new PokemontNotFoundException("Pokémon não encontrado na PokeAPI: " + nameOrId);
        }
    }

    public PokemonBattleCard obterCardBatalha(String nameOrId) {
        String busca = nameOrId.toLowerCase().trim();

        System.out.println("Consultando API externa PokeAPI para o Battle Card de: " + busca);

        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/" + busca;
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/" + busca;

        try {
            PokemonApiResponse pokeApi = restTemplate.getForObject(urlPokemon, PokemonApiResponse.class);
            PokemonSpeciesResponse speciesApi = restTemplate.getForObject(urlSpecies, PokemonSpeciesResponse.class);

            if (pokeApi == null || speciesApi == null) {
                throw new PokemontNotFoundException("Não foi possível obter dados para o Battle Card.");
            }

            PokemonBattleCard card = new PokemonBattleCard();
            String geracao = speciesApi.getGeneration() != null ? speciesApi.getGeneration().getName() : "unknown";
            card.setTitle(pokeApi.getName().toUpperCase() + " " + geracao);

            if (speciesApi.isLegendary()) {
                card.setClassification("Legendary Pokémon");
            } else if (speciesApi.isMythical()) {
                card.setClassification("Mythical Pokémon");
            } else if (speciesApi.isBaby()) {
                card.setClassification("Baby Pokémon");
            } else {
                card.setClassification("Common Pokémon");
            }

            String tipoPrincipal = "unknown";
            if (pokeApi.getTypes() != null && !pokeApi.getTypes().isEmpty()) {
                tipoPrincipal = pokeApi.getTypes().get(0).getType().getName();
            }
            card.setMainType(tipoPrincipal);

            String habilidadePrincipal = pokeApi.getAbilities().stream()
                    .filter(a -> !a.isHidden())
                    .map(a -> a.getAbility().getName())
                    .findFirst()
                    .orElse("None");
            card.setMainAbility(habilidadePrincipal);

            card.setPhysicalInfo("Height: " + pokeApi.getHeight() + " | Weight: " + pokeApi.getWeight());

            return card;
        } catch (HttpClientErrorException.NotFound e) {
            throw new PokemontNotFoundException("Pokémon não encontrado na PokeAPI: " + nameOrId);
        }
    }
}
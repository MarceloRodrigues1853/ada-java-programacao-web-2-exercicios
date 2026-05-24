package com.ada.aula3.controller;

import com.ada.aula3.model.PokemonBattleCard;
import com.ada.aula3.model.PokemonSummary;
import com.ada.aula3.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{nameOrId}/summary")
    public ResponseEntity<PokemonSummary> obterResumo(@PathVariable String nameOrId) {
        try {
            // Ajustado para obterResumoPokemon (com o "o" minúsculo e casado com o Service)
            PokemonSummary resumo = pokemonService.obterResumoPokemon(nameOrId);
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint do Desafio Extra: GET /api/pokemon/{nameOrId}/battle-card
     * Retorna a versão compacta estruturada para cartas de jogos.
     */
    @GetMapping("/{nameOrId}/battle-card")
    public ResponseEntity<PokemonBattleCard> obterCardBatalha(@PathVariable String nameOrId) {
        try {
            // Invoca a nova lógica de negócio do Service
            PokemonBattleCard card = pokemonService.obterCardBatalha(nameOrId);
            return ResponseEntity.ok(card); // Retorna 200 OK com o JSON do card
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o pokémon sumir
        }
    }
}
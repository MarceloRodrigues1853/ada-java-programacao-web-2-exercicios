package com.ada.aula3.controller;

import com.ada.aula3.model.PokemonBattleCard;
import com.ada.aula3.model.PokemonSummary;
import com.ada.aula3.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /*
     * PARTE 4 - Adicionar cache na consulta de Pokémon
     */
    @GetMapping("/{nameOrId}/summary")
    @Cacheable("pokemons")
    public ResponseEntity<PokemonSummary> obterResumo(@PathVariable String nameOrId) {
        PokemonSummary resumo = pokemonService.obterResumoPokemon(nameOrId);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/{nameOrId}/battle-card")
    @Cacheable("pokemons")
    public ResponseEntity<PokemonBattleCard> obterCardBatalha(@PathVariable String nameOrId) {
        PokemonBattleCard card = pokemonService.obterCardBatalha(nameOrId);
        return ResponseEntity.ok(card);
    }

    /*
     * PARTE 2 - Garantir que os endpoints POST executem as validações com @Valid
     */
    @PostMapping
    public ResponseEntity<PokemonSummary> criarPokemon(@Valid @RequestBody PokemonSummary pokemonSummary) {
        // Apenas retorna o objeto enviado para validar se o Bean Validation barra campos incorretos
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonSummary);
    }

    /*
     * Desafio Extra: Protegendo o Método
     */
    @PreAuthorize("hasRole('ADMIN')") // Exige que o usuário seja o ADMIN (neste caso, o oak)
    @PostMapping("/captured")
    public ResponseEntity<?> capturarPokemon(@RequestBody PokemonSummary pokemon) {
        // O código original de salvar o pokémon continua aqui dentro
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
    }

    // 1. Rota para LISTAR os capturados (Requer apenas estar logado - Ash ou Oak podem ver)
    @GetMapping("/captured")
    public ResponseEntity<String> listarCapturados() {
        return ResponseEntity.ok("Acesso Permitido: Aqui está a lista dos seus Pokémon capturados!");
    }

    // 2. Rota para LIBERAR um Pokémon (Requer ser ADMIN - Desafio Extra)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/captured/{id}")
    public ResponseEntity<String> liberarPokemon(@PathVariable("id") String id) {
        return ResponseEntity.ok("Acesso Permitido ADMIN: Pokémon " + id + " foi solto pelo Professor Oak!");
    }
}
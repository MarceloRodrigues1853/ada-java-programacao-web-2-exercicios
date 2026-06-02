package com.ada.aula3.exception;

/*
 * Exceção customizada para produto não encontrado.
 */

public class PokemontNotFoundException extends RuntimeException {

    public PokemontNotFoundException(String message) {
        super(message);
    }
}

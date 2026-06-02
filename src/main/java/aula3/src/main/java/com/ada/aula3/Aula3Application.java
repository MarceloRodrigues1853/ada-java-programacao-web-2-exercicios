package com.ada.aula3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/*
 * Classe principal da aplicação.
 *
 * @SpringBootApplication inicia o Spring Boot.
 *
 * @EnableCaching habilita o cache.
 * Sem ela, @Cacheable e @CacheEvict não funcionam.
 */

@SpringBootApplication
@EnableCaching // <-- ATIVA O SUPORTE A CACHE NATIVO DO SPRING [cite: 328]
public class Aula3Application {

	public static void main(String[] args) {
		SpringApplication.run(Aula3Application.class, args);
	}

}

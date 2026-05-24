package com.ada.aula3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Indica que é uma classe de configuração do Spring
public class RestTemplateConfig {

    @Bean // Cria e disponibiliza o RestTemplate para injeção em outras classes
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
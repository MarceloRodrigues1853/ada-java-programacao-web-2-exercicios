package com.ada.aula3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // ATIVA O DESAFIO EXTRA (Segurança direto no método)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("ash")
                .password("ash123")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("oak")
                .password("oak123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        // 1. Rota de listar capturados requer login comum
                        .requestMatchers(HttpMethod.GET, "/api/pokemon/captured").authenticated()

                        // 2. Rotas de busca gerais são públicas
                        .requestMatchers(HttpMethod.GET, "/api/pokemon/**").permitAll()

                        // 3. Rotas de capturar e soltar exigem ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/pokemon/captured").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pokemon/captured/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
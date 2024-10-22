package br.com.otavioluism.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean // anotation reponsavel por subscrever os metodos
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {// metodo responsavel por controlar os endpoints
        http.csrf(csrf -> csrf.disable()); // desabilitando a autorização e autenticacao as rotas
        return http.build();
    }
}

package com.olmcompany.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                            auth.requestMatchers("/candidate/").permitAll()
                                .requestMatchers("/company/").permitAll()
                                .requestMatchers("/company/auth/").permitAll()
                                .requestMatchers("/candidate/auth/").permitAll();
                            auth.anyRequest().authenticated();
                        }
                ).addFilterBefore(this.securityFilter, BasicAuthenticationFilter.class); // estou mudando a ordem do meu filtro, para rodar antes do authorizaHttpRequests

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

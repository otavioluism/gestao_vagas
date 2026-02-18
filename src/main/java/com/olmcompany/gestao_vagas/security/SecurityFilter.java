package com.olmcompany.gestao_vagas.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olmcompany.gestao_vagas.providers.JWTProvider;
import com.zaxxer.hikari.util.Credentials;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    public SecurityFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company")) {
            if (header != null) { // estamos limitando para somente interceptar o que for authorization
                DecodedJWT token = jwtProvider.validatedToken(header);
                if (token == null){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("company_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                // aqui estamos repassando a authenticacao e informacao para o fluxo do spring security
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        System.out.println(header);
        System.out.println("Passei por aqui e irei chamar o outro filtro!");

        filterChain.doFilter(request, response); // precisamos usar isso para chamar o controller senão não chega até ele
    }
}

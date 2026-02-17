package com.olmcompany.gestao_vagas.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olmcompany.gestao_vagas.providers.JWTProviderCandidate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilterCandidate extends OncePerRequestFilter {

    private final JWTProviderCandidate jwtProviderCandidate;

    public SecurityFilterCandidate(JWTProviderCandidate jwtProviderCandidate) {
        this.jwtProviderCandidate = jwtProviderCandidate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        SecurityContextHolder.getContext().setAuthentication(null);

        if(request.getRequestURI().startsWith("/candidate")){
            if(header != null) {
                DecodedJWT token = this.jwtProviderCandidate.validatedToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("candidate_id", token.getSubject());
                // aqui estamos repassando a authenticacao e informacao para o fluxo do spring security
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token.getSubject(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        System.out.println("Passei no filtro do candidate");

        filterChain.doFilter(request, response);
    }
}

package br.com.otavioluism.gestao_vagas.security;

import br.com.otavioluism.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        SecurityContextHolder.getContext().setAuthentication(null);

        String headerAuth = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company")) {
            if (headerAuth != null) {
                var tokenDecoded = this.jwtProvider.validateToken(headerAuth);
                if (tokenDecoded == null) { // se for vazio
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var roles = tokenDecoded.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                                        .toList();

                request.setAttribute("company_id", tokenDecoded.getSubject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth); // estamos inserindo para o spring security a variavel auth que nos denota como esta sendo autenticado as rotas
            }
        }

        filterChain.doFilter(request, response);
    }
}

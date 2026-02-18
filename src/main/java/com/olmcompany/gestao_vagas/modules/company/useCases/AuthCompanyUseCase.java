package com.olmcompany.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.olmcompany.gestao_vagas.exceptions.AuthException;
import com.olmcompany.gestao_vagas.exceptions.UserNotFoundException;
import com.olmcompany.gestao_vagas.modules.company.dto.AuthResponseCompanyDTO;
import com.olmcompany.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.olmcompany.gestao_vagas.modules.company.dto.AuthInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secretKey;

    @Value("${security.token.secret.expire.hours}")
    private String expiresTokenAt;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseCompanyDTO execute(AuthInfoDTO authInfoDTO) {

        var company = this.companyRepository.findByUsername(authInfoDTO.getUsername()).orElseThrow(
                () -> new UserNotFoundException("Username/Password invalid!")
        );

        var matchPassword = this.passwordEncoder.matches(authInfoDTO.getPassword(), company.getPassword());

        if (!matchPassword) {
            throw new AuthException("Username/Password invalid!");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(Integer.parseInt(expiresTokenAt)));

        var token = JWT.create().withIssuer("olmCompany")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        return AuthResponseCompanyDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }
}

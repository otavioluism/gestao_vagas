package com.olmcompany.gestao_vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.olmcompany.gestao_vagas.exceptions.AuthException;
import com.olmcompany.gestao_vagas.exceptions.UserNotFoundException;
import com.olmcompany.gestao_vagas.modules.candidate.CandidateRepository;
import com.olmcompany.gestao_vagas.modules.candidate.dto.AuthRequestCandidateDTO;
import com.olmcompany.gestao_vagas.modules.candidate.dto.AuthResponseCandidateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("security.token.secret.candidate")
    private String secretKey;

    @Value("${security.token.secret.expire.hours}")
    private String expiresTokenAt;

    private final CandidateRepository candidateRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseCandidateDTO execute(AuthRequestCandidateDTO authRequestCompanyDTO) {
        var candidate = this.candidateRepository.findByUsername(authRequestCompanyDTO.username())
                .orElseThrow(() -> new UserNotFoundException("Username/Password invalid!"));

        var matched = this.passwordEncoder.matches(authRequestCompanyDTO.password(), candidate.getPassword());

        if (!matched) {
            throw new AuthException("Username/Password invalid!");
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(Integer.parseInt(this.expiresTokenAt)));

        var token = JWT.create()
                .withIssuer("olmCompany")
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expiresIn)
                .withSubject(candidate.getId().toString())
                .sign(algorithm);


        return AuthResponseCandidateDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }

}

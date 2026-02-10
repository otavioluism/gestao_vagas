package com.olmcompany.gestao_vagas.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.olmcompany.gestao_vagas.exceptions.AuthException;
import com.olmcompany.gestao_vagas.exceptions.UserNotFoundException;
import com.olmcompany.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.olmcompany.gestao_vagas.modules.dto.AuthInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(AuthInfoDTO authInfoDTO) {

        var company = this.companyRepository.findByUsername(authInfoDTO.getUsername()).orElseThrow(
                () -> new UserNotFoundException("Username/Password invalid!")
        );

        var matchPassword = this.passwordEncoder.matches(authInfoDTO.getPassword(), company.getPassword());

        if (!matchPassword) {
            throw new AuthException("Username/Password invalid!");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create().withIssuer("olmCompany")
                .withSubject(company.getId().toString())
                .sign(algorithm);
    }
}

package com.olmcompany.gestao_vagas.security;

import com.olmcompany.gestao_vagas.exceptions.UserNotFoundException;
import com.olmcompany.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.olmcompany.gestao_vagas.modules.dto.AuthInfoDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCompanyUseCase {

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(AuthInfoDTO authInfoDTO) {

        var company = this.companyRepository.findByUsername(authInfoDTO.getUsername()).orElseThrow(
                () -> new UserNotFoundException("Company not found!")
        );

        var matchPassword = this.passwordEncoder.matches(authInfoDTO.getPassword(), company.getPassword());

        if (!matchPassword) {
            // error no login
        }

        // gerar o token jwt

    }
}

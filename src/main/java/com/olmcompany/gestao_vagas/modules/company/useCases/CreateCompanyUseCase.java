package com.olmcompany.gestao_vagas.modules.company.useCases;

import com.olmcompany.gestao_vagas.exceptions.UserFoundException;
import com.olmcompany.gestao_vagas.modules.company.entities.CompanyEntity;
import com.olmcompany.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public CreateCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException("Company já inserido!");
                });
        var responseEncoded = this.passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(responseEncoded);
        return this.companyRepository.save(companyEntity);
    }

}

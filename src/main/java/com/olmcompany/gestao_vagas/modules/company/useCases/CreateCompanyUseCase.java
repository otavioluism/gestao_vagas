package com.olmcompany.gestao_vagas.modules.company.useCases;

import com.olmcompany.gestao_vagas.exceptions.UserFoundException;
import com.olmcompany.gestao_vagas.modules.company.entities.CompanyEntity;
import com.olmcompany.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    private CompanyRepository companyRepository;

    public CreateCompanyUseCase(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository.findByUsernameOrName(companyEntity.getUsername(), companyEntity.getName())
                .ifPresent((user) -> {
                    throw new UserFoundException("Company já inserido!");
                });

        return this.companyRepository.save(companyEntity);
    }

}

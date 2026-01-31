package com.olmcompany.gestao_vagas.modules.candidate.useCases;

import com.olmcompany.gestao_vagas.exceptions.UserFoundException;
import com.olmcompany.gestao_vagas.modules.candidate.CandidateEntity;
import com.olmcompany.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    private CandidateRepository candidateRepository;

    public CreateCandidateUseCase(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent(candidate -> {
                    throw new UserFoundException("Candidate já existe!");
                });

        System.out.println("Imprimindo informação do candidato:");
        System.out.println(candidateEntity.getName());
        System.out.println(candidateEntity.getUsername());
        return this.candidateRepository.save(candidateEntity);
    }
}

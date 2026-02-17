package com.olmcompany.gestao_vagas.modules.candidate.useCases;

import com.olmcompany.gestao_vagas.exceptions.UserNotFoundException;
import com.olmcompany.gestao_vagas.modules.candidate.CandidateRepository;
import com.olmcompany.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public ProfileCandidateUseCase(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateResponseDTO execute(UUID id) {

        var candidate = this.candidateRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Candidate not found!"));

        return ProfileCandidateResponseDTO.builder()
                .email(candidate.getEmail())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .build();
    }
}

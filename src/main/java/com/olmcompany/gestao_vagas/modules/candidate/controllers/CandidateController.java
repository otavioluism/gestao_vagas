package com.olmcompany.gestao_vagas.modules.candidate.controllers;

import com.olmcompany.gestao_vagas.modules.candidate.CandidateEntity;
import com.olmcompany.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.olmcompany.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private CreateCandidateUseCase createCandidateUseCase;

    private ProfileCandidateUseCase profileCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase, ProfileCandidateUseCase profileCandidateUseCase){
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try {
            var response = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity get(HttpServletRequest request){
        var candidateId = request.getAttribute("candidate_id");
        try {
            var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(candidate);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

package br.com.otavioluism.gestao_vagas.modules.candidate.controllers;

import br.com.otavioluism.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.otavioluism.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CreateCandidateUseCase createCandidateUseCase;


    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var response = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

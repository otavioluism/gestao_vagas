package com.olmcompany.gestao_vagas.modules.candidate.controllers;

import com.olmcompany.gestao_vagas.modules.candidate.dto.AuthRequestCandidateDTO;
import com.olmcompany.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    private final AuthCandidateUseCase authCandidateUseCase;

    public AuthCandidateController(AuthCandidateUseCase authCandidateUseCase){
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth/")
    public ResponseEntity<Object> auth(@RequestBody AuthRequestCandidateDTO authRequestCandidateDTO) {
        try {
            var response = this.authCandidateUseCase.execute(authRequestCandidateDTO);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}

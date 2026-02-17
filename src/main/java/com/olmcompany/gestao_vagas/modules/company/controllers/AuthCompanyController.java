package com.olmcompany.gestao_vagas.modules.company.controllers;

import com.olmcompany.gestao_vagas.modules.dto.AuthInfoDTO;
import com.olmcompany.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    private AuthCompanyUseCase authCompanyUseCase;

    public AuthCompanyController(AuthCompanyUseCase authCompanyUseCase){
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth/")
    public ResponseEntity login(@RequestBody AuthInfoDTO authInfoDTO) {
        try {
            var token = this.authCompanyUseCase.execute(authInfoDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}

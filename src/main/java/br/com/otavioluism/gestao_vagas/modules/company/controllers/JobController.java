package br.com.otavioluism.gestao_vagas.modules.company.controllers;


import br.com.otavioluism.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.otavioluism.gestao_vagas.modules.company.entities.JobEntity;
import br.com.otavioluism.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    private ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id"); // buscando a informacao colocada dentro do request por meio do jwt

            var jobEntity = JobEntity.builder()
                    .companyId(UUID.fromString(companyId.toString()))
                    .benefits(createJobDTO.getBenefits())
                    .description(createJobDTO.getDescription())
                    .level(createJobDTO.getLevel())
                    .build(); // o builder ele cria uma instancia da classe, no caso um objeto

            var response = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
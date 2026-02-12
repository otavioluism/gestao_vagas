package com.olmcompany.gestao_vagas.modules.company.controllers;

import com.olmcompany.gestao_vagas.modules.company.entities.JobEntity;
import com.olmcompany.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import com.olmcompany.gestao_vagas.modules.dto.JobEntityDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody JobEntityDTO jobEntityDTO, HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id");

            var jobEntity = JobEntity.builder()
                    .benefits(jobEntityDTO.getBenefits())
                    .description(jobEntityDTO.getDescription())
                    .level(jobEntityDTO.getLevel())
                    .companyId(UUID.fromString(companyId.toString()))
                    .build();

            var response = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

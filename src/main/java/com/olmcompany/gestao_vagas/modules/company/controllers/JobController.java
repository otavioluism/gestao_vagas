package com.olmcompany.gestao_vagas.modules.company.controllers;

import com.olmcompany.gestao_vagas.modules.company.entities.JobEntity;
import com.olmcompany.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import com.olmcompany.gestao_vagas.modules.company.dto.JobRequestEntityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    private final CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase) {
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Rotas para controle de vagas") // renomeia a classe do controller
    @Operation(summary = "Criação de vagas para a empresa!", description = "Essa função é responsável por criar as vagas de uma empresa!") // coloca as descrições sobre a operação em si
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    ) // neste insere como será a saída quando der certo, lembrando que pode colocar mais de um
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity create(@Valid @RequestBody JobRequestEntityDTO jobEntityDTO, HttpServletRequest request) {
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

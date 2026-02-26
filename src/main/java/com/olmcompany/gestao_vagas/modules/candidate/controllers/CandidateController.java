package com.olmcompany.gestao_vagas.modules.candidate.controllers;

import com.olmcompany.gestao_vagas.modules.candidate.CandidateEntity;
import com.olmcompany.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.olmcompany.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import com.olmcompany.gestao_vagas.modules.company.entities.JobEntity;
import com.olmcompany.gestao_vagas.modules.company.useCases.ListAllJobsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;

    private final ProfileCandidateUseCase profileCandidateUseCase;

    private final ListAllJobsUseCase listAllJobsUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase,
                               ProfileCandidateUseCase profileCandidateUseCase,
                                ListAllJobsUseCase listAllJobsUseCase){
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
        this.listAllJobsUseCase = listAllJobsUseCase;
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

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')") // faz com que a rota precisa ter o token
    @Tag(name = "Candidato", description = "Informações do Candidato") // renomeia a classe do controller
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todos os jobs das empresas pelo filtro!") // coloca as descrições sobre a operação em si
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    ) // neste insere como será a saída quando der certo, lembrando que pode colocar mais de um
    public ResponseEntity getJobs(@RequestParam String filter) {
        try {
            var jobs = this.listAllJobsUseCase.execute(filter);
            return ResponseEntity.ok().body(jobs);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}

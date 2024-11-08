package br.com.otavioluism.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String username;
    private String description;

}

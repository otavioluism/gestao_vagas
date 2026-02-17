package com.olmcompany.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    private String email;
    private String name;
    private String description;
    private String username;
}

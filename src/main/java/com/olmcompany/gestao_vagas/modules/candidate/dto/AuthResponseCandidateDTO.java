package com.olmcompany.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponseCandidateDTO {
    private String access_token;
    private Long expires_in;
}

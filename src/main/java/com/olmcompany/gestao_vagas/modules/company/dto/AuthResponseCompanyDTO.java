package com.olmcompany.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponseCompanyDTO {
    private String access_token;
    private Long expires_in;
}

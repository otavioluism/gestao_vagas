package com.olmcompany.gestao_vagas.modules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthInfoDTO {

    private String username;
    private String password;

}

package com.olmcompany.gestao_vagas.modules.candidate;


import lombok.Data;

import java.util.UUID;

@Data
public class CandidateEntity {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String description;
    private String curriculum;

}

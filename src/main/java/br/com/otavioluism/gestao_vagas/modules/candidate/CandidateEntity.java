package br.com.otavioluism.gestao_vagas.modules.candidate;

import lombok.Data;

import java.util.UUID;

@Data// annotation com objetivo de inserir os getter e setter dos atribuos da classe
public class CandidateEntity {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String description;
    private String curriculum;

}

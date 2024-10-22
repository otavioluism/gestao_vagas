package br.com.otavioluism.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // cria-se o setter e o getter de todos os campos da classe
@AllArgsConstructor // cria um construtor com argumentos no nosso caso com dois argumentos
public class ErrorMessageDTO {

    private String message;
    private String field;

}

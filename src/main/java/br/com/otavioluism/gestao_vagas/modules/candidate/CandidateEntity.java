package br.com.otavioluism.gestao_vagas.modules.candidate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data// annotation com objetivo de inserir os getter e setter dos atribuos da classe
@Entity(name = "candidatos") // anotation entity transforma sua entidade em uma tabela no banco de dados, onde seus campos são os atributos com o mesmo nome
public class CandidateEntity {

    @Id // transformando o atributo em identificacao primary key a tabela
    @GeneratedValue(strategy = GenerationType.UUID) // utilizando a estrategia de criar-se sozinho o id para o candidato
    private UUID id;

    private String name;

    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido")
    private String email;

    @Length(min = 10, max = 100, message="A senha deve conter entre (10) e (100) caracteres") // minimo de 10 e maximo 100 caractereres para a senha
    private String password;

    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}

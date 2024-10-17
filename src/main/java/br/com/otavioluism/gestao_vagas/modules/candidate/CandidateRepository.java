package br.com.otavioluism.gestao_vagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// poderiamos utilizar a anotation @Repository, mas como JPA já tem dentro dela, ele já sabe se virar que é uma camada que se conecta ao banco de dados
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>  { // cria uma interface que extende da classe JPA onde passamos a entidade que se conecta ao banco e o id da entidade

}

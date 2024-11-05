package br.com.otavioluism.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder // não precisamos criar uma instanciação com o new Class, somente chamar o builder para setar os atributos
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private String benefits;

    @NotBlank(message = "Esse campo é obrigatório")
    private String level;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false) // fazendo o relacionamento da chave estrangeira da entidade Job (company_id) com a chave primaria (id) de Company
    private CompanyEntity companyEntity;

    @Column(name = "company_id")
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

package br.com.otavioluism.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private String benefits;

    private String level;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false) // fazendo o relacionamento da chave estrangeira da entidade Job (company_id) com a chave primaria (id) de Company
    private CompanyEntity companyEntity;

    @Column(name = "company_id")
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

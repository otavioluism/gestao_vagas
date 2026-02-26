package com.olmcompany.gestao_vagas.modules.company.useCases;

import com.olmcompany.gestao_vagas.modules.company.entities.JobEntity;
import com.olmcompany.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsUseCase {

    private final JobRepository jobRepository;

    private ListAllJobsUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> execute(String filter){
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}

package com.olmcompany.gestao_vagas.modules.company.useCases;

import com.olmcompany.gestao_vagas.modules.company.entities.JobEntity;
import com.olmcompany.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public CreateJobUseCase(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public JobEntity execute(JobEntity jobEntity) throws Exception {
        try {
            return this.jobRepository.save(jobEntity);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}

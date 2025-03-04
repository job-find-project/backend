package org.project.job.repository;

import org.junit.jupiter.api.Test;
import org.project.job.entity.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobTypeRepositoryTest {

    @Autowired private JobTypeRepository jobTypeRepository;

    @Test
    public void insertJobType() {
        JobType jobType = JobType.builder()
                .type("Toàn thời gian")
                .build();
        jobTypeRepository.save(jobType);
    }

}
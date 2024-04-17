package org.project.job.service.impl;

import org.project.job.entity.Job;
import org.project.job.repository.JobRepository;
import org.project.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired private JobRepository jobRepository;

    @Override
    public List<Job> getJobList(String token) {
        return jobRepository.findAll();
    }
}

package org.project.job.service.impl;

import org.project.job.entity.Job;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.JobRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired private JobRepository jobRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<Job> getJobList(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty()) {
           return null;
        }
        return jobRepository.findAll();
    }

    @Override
    public void toggleJob(String token, Long id) {
        Job job = jobRepository.findById(id).get();
        job.setIsActive(!job.getIsActive());
    }
}

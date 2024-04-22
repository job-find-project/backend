package org.project.job.service.impl;

import org.project.job.entity.Employer;
import org.project.job.entity.Job;
import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.EmployerRepository;
import org.project.job.repository.JobRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.response.JobResponse;
import org.project.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired private JobRepository jobRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private EmployerRepository employerRepository;

    @Override
    public List<JobResponse> getJobList(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty()) {
           return null;
        }
        User user = verificationToken.get().getUser();
        Employer employer = employerRepository.findByUser(user);
        List<Job> jobs = jobRepository.findByEmployer(employer);
        List<JobResponse> jobResponses = new ArrayList<>();

        for(Job job : jobs) {
            JobResponse jobResponse = JobResponse.builder()
                    .id(job.getId())
                    .title(job.getTitle())
                    .salary(job.getSalary())
                    .payrollPayment(job.getPayrollPayment())
                    .workAddress(job.getWorkAddress())
                    .types(job.getTypes())
                    .description(job.getDescription())
                    .diplomaRequire(job.getDiplomaRequire())
                    .genderRequire(job.getGenderRequire())
                    .workRequire(job.getWorkRequire())
                    .position(job.getPosition())
                    .build();
        }
        return jobResponses;
    }

    @Override
    public void toggleJob(String token, Long id) {
        Job job = jobRepository.findById(id).get();
        job.setIsActive(!job.getIsActive());
    }

    @Override
    public List<JobResponse> getJobs(Integer pageSize, Integer pageNumber, String sort) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Job> jobs = jobRepository.findAll(page).getContent();
        List<JobResponse> jobResponses = new ArrayList<>();

        for(Job job : jobs) {
            JobResponse jobResponse = JobResponse.builder()
                    .id(job.getId())
                    .title(job.getTitle())
                    .salary(job.getSalary())
                    .payrollPayment(job.getPayrollPayment())
                    .workAddress(job.getWorkAddress())
                    .types(job.getTypes())
                    .description(job.getDescription())
                    .diplomaRequire(job.getDiplomaRequire())
                    .genderRequire(job.getGenderRequire())
                    .workRequire(job.getWorkRequire())
                    .position(job.getPosition())
                    .build();
        }

        return jobResponses;
    }
}

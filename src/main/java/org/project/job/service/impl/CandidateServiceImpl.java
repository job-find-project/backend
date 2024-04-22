package org.project.job.service.impl;

import org.project.job.entity.Candidate;
import org.project.job.entity.Job;
import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.CandidateRepository;
import org.project.job.repository.JobRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired private CandidateRepository candidateRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private JobRepository jobRepository;

    @Override
    public String submitCV(String token, MultipartFile cv, Long id) throws IOException, SQLException {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(verificationTokenOptional.isEmpty()) {
            return "invalid token";
        }
        if(jobOptional.isEmpty()) {
            return "invalid job";
        }
        Job job = jobOptional.get();
        User user = verificationTokenOptional.get().getUser();
        Candidate candidate = candidateRepository.findByUser(user);
        if(candidate == null) {
            candidate = new Candidate();
        }
        candidate.setCV(new SerialBlob(cv.getBytes()));
        job.getCandidates().add(candidate);

        candidateRepository.save(candidate);
        jobRepository.save(job);
        return "valid";
    }
}

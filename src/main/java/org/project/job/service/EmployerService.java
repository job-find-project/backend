package org.project.job.service;

import org.project.job.dto.EmployerDto;
import org.project.job.dto.JobDto;

public interface EmployerService {
    String registerEmployer(String token, EmployerDto employerDto);

    String postJob(String token, JobDto jobDto);
}

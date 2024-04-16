package org.project.job.service;

import org.project.job.dto.EmployerDto;

public interface EmployerService {
    String registerEmployer(String token, EmployerDto employerDto);
}

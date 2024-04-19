package org.project.job.service;

import org.project.job.entity.Job;

import java.util.List;

public interface JobService {
    List<Job> getJobList(String token);

    void toggleJob(String token, Long id);
}

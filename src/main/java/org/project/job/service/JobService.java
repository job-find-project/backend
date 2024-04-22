package org.project.job.service;

import org.project.job.entity.Job;
import org.project.job.response.JobResponse;

import java.util.List;

public interface JobService {
    List<JobResponse> getJobList(String token);

    void toggleJob(String token, Long id);

    List<JobResponse> getJobs(Integer pageSize, Integer pageNumber, String sort);
}

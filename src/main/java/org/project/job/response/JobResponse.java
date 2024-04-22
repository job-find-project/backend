package org.project.job.response;

import lombok.Builder;
import lombok.Data;
import org.project.job.entity.JobType;

import java.util.List;

@Data
@Builder
public class JobResponse {
    private Long id;
    private String title;
    private Integer salary;
    private String payrollPayment;
    private String workAddress;
    private String position;
    private String jobType;
    private String description;
    private String diplomaRequire;
    private String workRequire;
    private String genderRequire;
    private List<JobType> types;
}

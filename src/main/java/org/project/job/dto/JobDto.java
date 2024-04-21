package org.project.job.dto;

import lombok.Data;

@Data
public class JobDto {
    private String token;
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
}

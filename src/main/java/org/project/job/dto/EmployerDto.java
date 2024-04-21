package org.project.job.dto;

import lombok.Data;

@Data
public class EmployerDto {
    private String token;
    private String fullName;
    private String mobilePhone;
    private String companyName;
    private String companySector;
    private String companyDescription;
    private String companyAddress;
}

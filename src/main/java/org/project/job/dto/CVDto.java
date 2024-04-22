package org.project.job.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CVDto {
    private String token;
    private MultipartFile CV;
}

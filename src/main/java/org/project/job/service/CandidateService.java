package org.project.job.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface CandidateService {
    String submitCV(String token, MultipartFile cv, Long jobID) throws IOException, SQLException;
}

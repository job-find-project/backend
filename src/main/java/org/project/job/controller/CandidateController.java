package org.project.job.controller;

import org.project.job.dto.CVDto;
import org.project.job.response.MessageResponse;
import org.project.job.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/candidate")
@CrossOrigin(origins = "*")
public class CandidateController {

    @Autowired private CandidateService candidateService;

    @PutMapping("/submit-cv")
    public ResponseEntity<?> submitCV(@RequestParam String token, @RequestParam MultipartFile CV, @RequestParam Long jobId) throws SQLException, IOException {
        String message = candidateService.submitCV(token, CV, jobId);
        return message.equals("valid") ?
                ResponseEntity.ok(new MessageResponse(message)) :
                ResponseEntity.badRequest().body(new MessageResponse(message));
//        return ResponseEntity.ok(jobId);
    }
}

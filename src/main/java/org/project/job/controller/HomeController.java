package org.project.job.controller;

import org.project.job.entity.Job;
import org.project.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {

    @Autowired private JobService jobService;

    @GetMapping("/getJobs")
    public ResponseEntity<?> getJobs(@RequestParam Integer pageNumber,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(required = false) String sort) {
        List<Job> jobs = jobService.getJobs(pageSize, pageNumber, sort);
        return ResponseEntity.ok(jobs);
    }

}
